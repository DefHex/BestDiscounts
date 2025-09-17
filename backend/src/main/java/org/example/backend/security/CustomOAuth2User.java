package org.example.backend.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, AppUserPrincipal {

    private final OAuth2User oauth2User;
    @Getter
    private final AppUser appUser;

    public CustomOAuth2User(OAuth2User oauth2User, AppUser appUser) {
        this.oauth2User = oauth2User;
        this.appUser = appUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        // Return the unique ID from our AppUser, which is more reliable and consistent.
        return appUser.id();
    }

}