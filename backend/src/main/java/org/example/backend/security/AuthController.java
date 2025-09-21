package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @GetMapping("/me")
    public AppUserDto getMe(@AuthenticationPrincipal AppUserPrincipal user){
        if (user == null || user.getAppUser() == null) {
            return null;
        }
        AppUser appUser=user.getAppUser();
        return new AppUserDto(appUser.id(),appUser.userName(),appUser.avatarUrl(), appUser.shoppingCart());
    }
}
