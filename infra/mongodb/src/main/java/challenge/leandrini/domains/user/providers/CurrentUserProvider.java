package challenge.leandrini.domains.user.providers;

import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import challenge.leandrini.domains.users.usecases.create.IGetUserByEmailGateway;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserProvider implements ICurrentUserGateway {

    private final IGetUserByEmailGateway getUserByEmailGateway;

    public CurrentUserProvider(IGetUserByEmailGateway getUserByEmailGateway) {
        this.getUserByEmailGateway = getUserByEmailGateway;
    }

    @Override
    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        String roleString = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a != null && a.startsWith("ROLE_"))
                .findFirst()
                .map(a -> a.substring("ROLE_".length()))
                .orElse("ASSISTANT");

        UserRole role = parseUserRole(roleString);

        Object principal = auth.getPrincipal();

        if (principal instanceof Jwt jwt) {
            String username = firstNonBlank(
                    jwt.getClaimAsString("preferred_username"),
                    jwt.getClaimAsString("email"),
                    jwt.getSubject()
            );

            String email = jwt.getClaimAsString("email");
            String storeId = jwt.getClaimAsString("store_id");

            return new User(
                    jwt.getSubject(),
                    username,
                    email,
                    null,
                    null,
                    role,
                    storeId,
                    null,
                    null
            );
        }

        if (principal instanceof UserDetails ud) {
            String username = ud.getUsername();

            if ("admin".equals(username) || "owner".equals(username) ||
                    "manager".equals(username) || "assistant".equals(username)) {
                return new User(
                        null,
                        username,
                        username + "@local",
                        null,
                        null,
                        role,
                        null,
                        null,
                        null
                );
            }

            Optional<User> dbUser = getUserByEmailGateway.execute(username);
            if (dbUser.isPresent()) {
                return dbUser.get();
            }

            return new User(
                    null,
                    username,
                    username,
                    null,
                    null,
                    role,
                    null,
                    null,
                    null
            );
        }

        return new User(
                null,
                String.valueOf(principal),
                null,
                null,
                null,
                role,
                null,
                null,
                null
        );
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) return v;
        }
        return null;
    }

    private static UserRole parseUserRole(String roleString) {
        if (roleString == null || roleString.trim().isEmpty()) {
            return UserRole.ASSISTANT;
        }

        try {
            return UserRole.valueOf(roleString.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            return UserRole.ASSISTANT;
        }
    }
}