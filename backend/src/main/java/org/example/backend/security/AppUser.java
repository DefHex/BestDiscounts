package org.example.backend.security;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record AppUser(String id, String providerId, String provider, String userName, String avatarUrl, List<String> shoppingCart) {
}
