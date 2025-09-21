package org.example.backend.security;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record AppUserDto(String id, String userName, String avatarUrl, List<String> shoppingCart) {
}
