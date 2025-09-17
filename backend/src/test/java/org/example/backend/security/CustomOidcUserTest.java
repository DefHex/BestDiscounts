package org.example.backend.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CustomOidcUserTest {

    @Mock
    private OidcUser oidcUser;

    @Mock
    private AppUser appUser;

    private CustomOidcUser customOidcUser;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        customOidcUser = new CustomOidcUser(oidcUser, appUser);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getClaims() {
        Map<String, Object> claims = Collections.singletonMap("claim", "value");
        when(oidcUser.getClaims()).thenReturn(claims);
        assertEquals(claims, customOidcUser.getClaims());
    }

    @Test
    void getUserInfo() {
        OidcUserInfo userInfo = new OidcUserInfo(Collections.singletonMap("info", "value"));
        when(oidcUser.getUserInfo()).thenReturn(userInfo);
        assertEquals(userInfo, customOidcUser.getUserInfo());
    }

    @Test
    void getIdToken() {
        OidcIdToken idToken = new OidcIdToken("tokenValue", null, null, Collections.singletonMap("claim", "value"));
        when(oidcUser.getIdToken()).thenReturn(idToken);
        assertEquals(idToken, customOidcUser.getIdToken());
    }

    @Test
    void getAttributes() {
        Map<String, Object> attributes = Collections.singletonMap("attribute", "value");
        when(oidcUser.getAttributes()).thenReturn(attributes);
        assertEquals(attributes, customOidcUser.getAttributes());
    }


    @Test
    void getName() {
        String userId = "testUserId";
        when(appUser.id()).thenReturn(userId);
        assertEquals(userId, customOidcUser.getName());
    }

    @Test
    void getAppUser() {
        assertEquals(appUser, customOidcUser.getAppUser());
    }
}
