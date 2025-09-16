package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AppUserRepository userRepo;

    // Package-private for easy testing
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getName();
        String uniqueId = provider + ":" + providerId;

        AppUser appUser = userRepo.findById(uniqueId)
                .orElseGet(() -> createAppUser(oAuth2User, uniqueId, provider, providerId));

        System.out.println("Loaded user: " + appUser.userName() + " with ID: " + appUser.id());
        return new CustomOAuth2User(oAuth2User, appUser);
    }

    private AppUser createAppUser(OAuth2User oAuth2User, String uniqueId, String provider, String providerId) {
        String name = null;
        String avatarUrl = null;

        switch (provider) {
            case "github":
                name = oAuth2User.getAttribute("login");
                avatarUrl = oAuth2User.getAttribute("avatar_url");
                break;
            case "google":
                name = oAuth2User.getAttribute("name");
                avatarUrl = oAuth2User.getAttribute("picture");
                break;
            case "facebook":
                name = oAuth2User.getAttribute("name");
                Map<String, Object> picture = oAuth2User.getAttribute("picture");
                if (picture != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> data = (Map<String, Object>) picture.get("data");
                    if (data != null) {
                        avatarUrl = (String) data.get("url");
                    }
                }
                break;
        }

        AppUser newUser = AppUser.builder()
                .id(uniqueId)
                .provider(provider)
                .providerId(providerId)
                .userName(name)
                .avatarUrl(avatarUrl)
                .build();

        return userRepo.save(newUser);
    }
}
