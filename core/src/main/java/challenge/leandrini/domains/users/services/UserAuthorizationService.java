package challenge.leandrini.domains.users.services;

import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationService {

    public boolean isAdmin(User user) {
        return user != null && UserRole.ADMIN.equals(user.getRole());
    }

    public boolean isOwnerOrManager(User user) {
        if (user == null) return false;
        UserRole userRole = user.getRole();
        return UserRole.OWNER.equals(userRole) || UserRole.MANAGER.equals(userRole);
    }

    public void validateUserCreationPermission(User currentUser, UserRole newUserRole, String newUserStoreId) {
        if (currentUser == null) {
            throw new AccessDeniedException("User must be authenticated");
        }

        UserRole currentUserRole = currentUser.getRole();
        String currentUserStoreId = currentUser.getStoreId();

        if (UserRole.ADMIN.equals(currentUserRole)) {
            return;
        }

        if (UserRole.OWNER.equals(currentUserRole) || UserRole.MANAGER.equals(currentUserRole)) {
            if (currentUserStoreId == null || currentUserStoreId.trim().isEmpty()) {
                throw new AccessDeniedException("Store ID is required for owners and managers");
            }

            if (newUserStoreId == null || newUserStoreId.trim().isEmpty()) {
                throw new AccessDeniedException("Store ID is required for the new user");
            }

            if (!currentUserStoreId.equals(newUserStoreId)) {
                throw new AccessDeniedException("Owners and managers can only create users in their own store");
            }

            if (UserRole.ADMIN.equals(newUserRole)) {
                throw new AccessDeniedException("Only administrators can create administrator users");
            }

            return;
        }

        throw new AccessDeniedException("Insufficient permissions to create users");
    }

    public void validateUserEditPermission(User currentUser, User targetUser) {
        if (currentUser == null) {
            throw new AccessDeniedException("User must be authenticated");
        }

        UserRole currentUserRole = currentUser.getRole();
        String currentUserStoreId = currentUser.getStoreId();
        String targetUserStoreId = targetUser.getStoreId();

        if (UserRole.ADMIN.equals(currentUserRole)) {
            return;
        }

        if (UserRole.OWNER.equals(currentUserRole) || UserRole.MANAGER.equals(currentUserRole)) {
            if (currentUserStoreId == null || currentUserStoreId.trim().isEmpty()) {
                throw new AccessDeniedException("Store ID is required for owners and managers");
            }

            if (targetUserStoreId == null || targetUserStoreId.trim().isEmpty()) {
                throw new AccessDeniedException("Target user must have a store ID");
            }

            if (!currentUserStoreId.equals(targetUserStoreId)) {
                throw new AccessDeniedException("Owners and managers can only edit users in their own store");
            }

            return;
        }

        throw new AccessDeniedException("Insufficient permissions to edit users");
    }

//    public void validateRoleAssignment(User currentUser, String newRole) {
//        if (newRole == null) return;
//        if (UserRole.ADMIN.name().equals(newRole) && !isAdmin(currentUser)) {
//            throw new AccessDeniedException("Only administrators can assign ADMIN role");
//        }
//    }
//
//    public void validateStoreChange(User currentUser, User targetUser, String newStoreId) {
//        if (newStoreId == null) return;
//        if (isAdmin(currentUser)) return;
//
//        if (!isOwnerOrManager(currentUser)) {
//            throw new AccessDeniedException("Insufficient permissions to change store");
//        }
//
//        String currentStore = nvl(currentUser.getStoreId());
//        if (currentStore.isEmpty()) {
//            throw new AccessDeniedException("Store ID is required for owners and managers");
//        }
//        if (!currentStore.equals(newStoreId)) {
//            throw new AccessDeniedException("Owners and managers can only move users within their own store");
//        }
//    }

    public String resolveEffectiveStoreForListing(User currentUser, String requestedStoreId) {
        if (currentUser == null) throw new AccessDeniedException("User must be authenticated");
        if (isAdmin(currentUser)) {
            return requestedStoreId;
        }
        if (!isOwnerOrManager(currentUser)) {
            throw new AccessDeniedException("Insufficient permissions to list users");
        }
        String currentStore = nvl(currentUser.getStoreId());
        if (currentStore.isEmpty()) {
            throw new AccessDeniedException("Store ID is required for owners and managers");
        }
        if (requestedStoreId != null && !requestedStoreId.equals(currentStore)) {
            throw new AccessDeniedException("Owners/managers can list only their own store");
        }
        return currentStore;
    }

    private static String nvl(String s) { return s == null ? "" : s.trim(); }
} 