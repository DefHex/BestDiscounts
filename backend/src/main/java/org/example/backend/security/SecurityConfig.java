package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${app.url}")
    private String appUrl;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a-> a
//                        .requestMatchers("/api/auth/params").permitAll()
                        .requestMatchers("/api/auth/me").authenticated()
                        .requestMatchers("/api/secured").authenticated()
//                        .requestMatchers("/api/discounts").authenticated()
                        .anyRequest().permitAll())
                .logout(l->l.logoutSuccessUrl(appUrl))
                .oauth2Login(o->o
                        .defaultSuccessUrl(appUrl)
                        .userInfoEndpoint(u->u
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService)))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        return http.build();
    }
}
