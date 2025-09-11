package org.example.backend.security;

import lombok.Builder;

@Builder
public record AppUser(String id,String providerId,String provider,String userName, String avatarUrl) {
}
