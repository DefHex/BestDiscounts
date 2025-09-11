package org.example.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMe_whenLoggedInWithOidc_returnsAppUserDto() throws Exception {
        OidcUser mockOidcUser = mock(OidcUser.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me")
                        .with(oidcLogin().oidcUser(
                                new CustomOidcUser(mockOidcUser,
                                        new AppUser("google:123456789",
                                                "123456789",
                                                "google",
                                                "George",
                                                "George.jpg"))
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("google:123456789"))
                .andExpect(jsonPath("$.userName").value("George"))
                .andExpect(jsonPath("$.avatarUrl").value("George.jpg"));
    }

    @Test
    void getMe_whenLoggedInWithOAuth2_returnsAppUserDto() throws Exception {
        OAuth2User mockOAuth2User = mock(OAuth2User.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me")
                        .with(oauth2Login().oauth2User(
                                new CustomOAuth2User(mockOAuth2User,
                                        new AppUser("github:123456789",
                                                "123456789",
                                                "github",
                                                "Gerry",
                                                "Gerry.jpg"))
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("github:123456789"))
                .andExpect(jsonPath("$.userName").value("Gerry"))
                .andExpect(jsonPath("$.avatarUrl").value("Gerry.jpg"));
    }
}
