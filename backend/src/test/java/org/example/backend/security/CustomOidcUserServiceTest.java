package org.example.backend.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomOidcUserServiceTest {

    @Mock
    private AppUserRepository userRepo;

    @InjectMocks
    private CustomOidcUserService customOidcUserService;

    @Mock
    private OidcUserService oidcUserServiceDelegate;

    @Mock
    private OidcUserRequest oidcUserRequest;

    @Mock
    private ClientRegistration clientRegistration;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        customOidcUserService.delegate = oidcUserServiceDelegate; // Replace the real delegate with our mock
        when(oidcUserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn("google");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void loadUser_whenUserExists() {
        // Given
        String providerId = "12345";
        String uniqueId = "google:" + providerId;
        Map<String, Object> claims = Map.of("sub", providerId, "name", "Test User", "picture", "https://example.com/pic.jpg");
        OidcIdToken idToken = new OidcIdToken("token-value", Instant.now(), Instant.now().plusSeconds(60), claims);
        OidcUser oidcUser = new DefaultOidcUser(null, idToken);

        AppUser existingUser = AppUser.builder()
                .id(uniqueId)
                .userName("Test User")
                .provider("google")
                .providerId(providerId)
                .build();
        when(oidcUserServiceDelegate.loadUser(oidcUserRequest)).thenReturn(oidcUser);
        when(userRepo.findById(uniqueId)).thenReturn(Optional.of(existingUser));

        // When
        OidcUser result = customOidcUserService.loadUser(oidcUserRequest);

        // Then
        assertInstanceOf(CustomOidcUser.class, result);
        CustomOidcUser customOidcUser = (CustomOidcUser) result;
        assertEquals(existingUser.id(), customOidcUser.getName());
        assertEquals(existingUser, customOidcUser.getAppUser());
        verify(userRepo, never()).save(any(AppUser.class));
    }

    @Test
    void loadUser_whenNewUser() {
        // Given
        String providerId = "54321";
        String uniqueId = "google:" + providerId;
        Map<String, Object> claims = Map.of("sub", providerId, "name", "New User", "picture", "https://example.com/new.jpg");
        OidcIdToken idToken = new OidcIdToken("token-value", Instant.now(), Instant.now().plusSeconds(60), claims);
        OidcUser oidcUser = new DefaultOidcUser(null, idToken);

        ArgumentCaptor<AppUser> appUserCaptor = ArgumentCaptor.forClass(AppUser.class);

        when(oidcUserServiceDelegate.loadUser(oidcUserRequest)).thenReturn(oidcUser);
        when(userRepo.findById(uniqueId)).thenReturn(Optional.empty());
        when(userRepo.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        OidcUser result = customOidcUserService.loadUser(oidcUserRequest);

        // Then
        assertInstanceOf(CustomOidcUser.class, result);
        CustomOidcUser customOidcUser = (CustomOidcUser) result;

        verify(userRepo, times(1)).save(appUserCaptor.capture());
        AppUser savedUser = appUserCaptor.getValue();

        assertEquals(uniqueId, savedUser.id());
        assertEquals("New User", savedUser.userName());
        assertEquals("https://example.com/new.jpg", savedUser.avatarUrl());
        assertEquals("google", savedUser.provider());
        assertEquals(providerId, savedUser.providerId());

        assertEquals(savedUser.id(), customOidcUser.getName());
        assertEquals(savedUser, customOidcUser.getAppUser());
    }
}
