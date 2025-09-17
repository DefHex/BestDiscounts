package org.example.backend.security;

// An interface to unify our custom principal types
public interface AppUserPrincipal {
    AppUser getAppUser();
}