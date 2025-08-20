package challenge.leandrini.domains.users.services;

import challenge.leandrini.domains.users.models.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("userAuth")
public class UserAuthorizationExpressions {

    public boolean canCreateUser(Authentication authentication, String newUserRole, String newUserStoreId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ROLE_ADMIN"));

        if (isAdmin) {
            return true;
        }

        boolean isOwnerOrManager = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ROLE_OWNER") || authority.equals("ROLE_MANAGER"));

        if (isOwnerOrManager) {
            if (UserRole.ADMIN.name().equals(newUserRole)) {
                return false;
            }
            return newUserStoreId != null && !newUserStoreId.trim().isEmpty();
        }

        return false;
    }

    public boolean canEditUserProfiles(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> 
                    authority.equals("ROLE_ADMIN") || 
                    authority.equals("ROLE_OWNER") || 
                    authority.equals("ROLE_MANAGER")
                );
    }
} 