package org.example.backend.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomOAuth2UserServiceTest {

    @Mock
    private AppUserRepository userRepo;

    @InjectMocks
    private CustomOAuth2UserService customOAuth2UserService;

    @Mock
    private OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate;

    @Mock
    private OAuth2UserRequest userRequest;

    @Mock
    private ClientRegistration clientRegistration;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        customOAuth2UserService.delegate = delegate;
        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void loadUser_whenUserExists() {
        // Given
        String provider = "github";
        String providerId = "12345";
        String uniqueId = provider + ":" + providerId;

        // Correctly set up the mock OAuth2User to use the 'id' attribute for getName()
        Map<String, Object> attributes = Map.of("login", "testuser", "id", 12345);
        OAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), attributes, "id");

        AppUser existingUser = AppUser.builder().id(uniqueId).userName("testuser").build();

        when(clientRegistration.getRegistrationId()).thenReturn(provider);
        when(delegate.loadUser(userRequest)).thenReturn(oAuth2User);
        when(userRepo.findById(uniqueId)).thenReturn(Optional.of(existingUser));

        // When
        OAuth2User result = customOAuth2UserService.loadUser(userRequest);

        // Then
        assertInstanceOf(CustomOAuth2User.class, result);
        assertEquals(existingUser.id(), result.getName());
        verify(userRepo, never()).save(any(AppUser.class));
    }

    @Test
    void loadUser_whenNewGitHubUser() {
        // Given
        String provider = "github";
        String providerId = "12345";
        String uniqueId = provider + ":" + providerId;
        String name = "githubuser";
        String avatarUrl = "https://example.com/avatar.jpg";

        Map<String, Object> attributes = Map.of("login", name, "avatar_url", avatarUrl, "id", 12345);
        OAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), attributes, "id");

        when(clientRegistration.getRegistrationId()).thenReturn(provider);
        when(delegate.loadUser(userRequest)).thenReturn(oAuth2User);
        when(userRepo.findById(uniqueId)).thenReturn(Optional.empty());
        when(userRepo.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        customOAuth2UserService.loadUser(userRequest);

        // Then
        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepo).save(userCaptor.capture());
        AppUser savedUser = userCaptor.getValue();

        assertEquals(uniqueId, savedUser.id());
        assertEquals(name, savedUser.userName());
        assertEquals(avatarUrl, savedUser.avatarUrl());
    }

    @Test
    void loadUser_whenNewGoogleUser() {
        // Given
        String provider = "google";
        String providerId = "123456789";
        String uniqueId = provider + ":" + providerId;
        String name = "Google User";
        String avatarUrl = "https://example.com/google.jpg";

        Map<String, Object> attributes = Map.of("sub", providerId, "name", name, "picture", avatarUrl);
        OAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), attributes, "sub");

        when(clientRegistration.getRegistrationId()).thenReturn(provider);
        when(delegate.loadUser(userRequest)).thenReturn(oAuth2User);
        when(userRepo.findById(uniqueId)).thenReturn(Optional.empty());
        when(userRepo.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        customOAuth2UserService.loadUser(userRequest);

        // Then
        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepo).save(userCaptor.capture());
        AppUser savedUser = userCaptor.getValue();

        assertEquals(uniqueId, savedUser.id());
        assertEquals(name, savedUser.userName());
        assertEquals(avatarUrl, savedUser.avatarUrl());
    }

    @Test
    void loadUser_whenNewFacebookUser() {
        // Given
        String provider = "facebook";
        String providerId = "987654321";
        String uniqueId = provider + ":" + providerId;
        String name = "Facebook User";
        String avatarUrl = "https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=123";

        Map<String, Object> pictureData = Map.of("url", avatarUrl);
        Map<String, Object> picture = Map.of("data", pictureData);
        Map<String, Object> attributes = Map.of("id", providerId, "name", name, "picture", picture);
        OAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), attributes, "id");

        when(clientRegistration.getRegistrationId()).thenReturn(provider);
        when(delegate.loadUser(userRequest)).thenReturn(oAuth2User);
        when(userRepo.findById(uniqueId)).thenReturn(Optional.empty());
        when(userRepo.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        customOAuth2UserService.loadUser(userRequest);

        // Then
        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepo).save(userCaptor.capture());
        AppUser savedUser = userCaptor.getValue();

        assertEquals(uniqueId, savedUser.id());
        assertEquals(name, savedUser.userName());
        assertEquals(avatarUrl, savedUser.avatarUrl());
    }

    @Test
    void loadUser_whenFacebookUserWithNoPictureData() {
        // Given
        String provider = "facebook";
        String providerId = "987654321";
        String uniqueId = provider + ":" + providerId;
        String name = "Facebook User";

        Map<String, Object> picture = Map.of("data", Collections.emptyMap());
        Map<String, Object> attributes = Map.of("id", providerId, "name", name, "picture", picture);
        OAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), attributes, "id");

        when(clientRegistration.getRegistrationId()).thenReturn(provider);
        when(delegate.loadUser(userRequest)).thenReturn(oAuth2User);
        when(userRepo.findById(uniqueId)).thenReturn(Optional.empty());
        when(userRepo.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        customOAuth2UserService.loadUser(userRequest);

        // Then
        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepo).save(userCaptor.capture());
        AppUser savedUser = userCaptor.getValue();

        assertNull(savedUser.avatarUrl());
    }
}
