package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final AppUserRepository userRepo;

    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 'sub' is the standard OIDC claim for user's unique ID
        String providerId = oidcUser.getSubject();
        String uniqueId = provider + ":" + providerId;

        AppUser appUser = userRepo.findById(uniqueId)
                .orElseGet(() -> createAppUser(oidcUser, uniqueId, provider, providerId));
        System.out.println("Loaded user: " + appUser.userName() + " with ID: " + appUser.id());
        return new CustomOidcUser(oidcUser, appUser);
    }

    private AppUser createAppUser(OidcUser oidcUser, String uniqueId, String provider, String providerId) {
        Map<String, Object> attributes = oidcUser.getAttributes();
        String name = attributes.get("name").toString();
        String avatarUrl = attributes.get("picture").toString();

        AppUser newUser = AppUser.builder()
                .id(uniqueId)
                .provider(provider)
                .providerId(providerId)
                .userName(name)
                .avatarUrl(avatarUrl)
                .build();

        System.out.println("Saved user:"+ newUser);
        return userRepo.save(newUser);
    }
}