package challenge.leandrini.config;

import challenge.leandrini.domains.users.usecases.create.IGetUserByEmailGateway;
import challenge.leandrini.domains.users.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IGetUserByEmailGateway getUserByEmailGateway;
    
    private final Map<String, HardcodedUser> hardcodedUsers = Map.of(
        "admin", new HardcodedUser("admin", "{noop}admin123", "ADMIN"),
        "owner", new HardcodedUser("owner", "{noop}owner123", "OWNER"),
        "manager", new HardcodedUser("manager", "{noop}manager123", "MANAGER"),
        "assistant", new HardcodedUser("assistant", "{noop}assistant123", "ASSISTANT")
    );

    public CustomUserDetailsService(IGetUserByEmailGateway getUserByEmailGateway) {
        this.getUserByEmailGateway = getUserByEmailGateway;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (hardcodedUsers.containsKey(username)) {
            HardcodedUser hardcodedUser = hardcodedUsers.get(username);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(hardcodedUser.username)
                    .password(hardcodedUser.password)
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + hardcodedUser.role)))
                    .build();
        }
        
        Optional<User> userOptional = getUserByEmailGateway.execute(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                    .build();
        }
        
        throw new UsernameNotFoundException("User not found: " + username);
    }
    
    private static class HardcodedUser {
        final String username;
        final String password;
        final String role;
        
        HardcodedUser(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }
}