package org.example.backend.security;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record AppUserDto(String id,String userName, String avatarUrl) {
}
