package com.projectarchitect.util;

import com.projectarchitect.exception.UnauthorizedException;
import com.projectarchitect.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility helper for accessing the currently authenticated user's details
 * from the Spring Security context.
 */
@Component
public class SecurityUtil {

    /**
     * Returns the {@link SecurityUser} principal for the currently authenticated request.
     *
     * @throws UnauthorizedException if no authenticated user is present in the context
     */
    public SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof SecurityUser)) {
            throw new UnauthorizedException("No authenticated user found in the current security context");
        }
        return (SecurityUser) authentication.getPrincipal();
    }

    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Checks whether the currently authenticated user holds the ADMIN role.
     */
    public boolean isCurrentUserAdmin() {
        return getCurrentUser().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ROLE_ADMIN"));
    }
}
