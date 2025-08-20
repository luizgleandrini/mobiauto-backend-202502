package challenge.leandrini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**","/swagger-ui/**").permitAll()
                        .requestMatchers("/v1/users/**").authenticated()
                        .requestMatchers("/v1/dealerships/**").authenticated()
                        .requestMatchers("/v1/opportunities/**").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = Map.of(
            encodingId, new BCryptPasswordEncoder(),
            "noop", NoOpPasswordEncoder.getInstance()
        );
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder() {
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword.startsWith("$2a$") || encodedPassword.startsWith("$2b$") ||
                    encodedPassword.startsWith("$2y$")) {
                    return super.matches(rawPassword, encodedPassword);
                }
                return rawPassword.toString().equals(encodedPassword);
            }
        };
        
        passwordEncoder.setDefaultPasswordEncoderForMatches(bcryptEncoder);
        return passwordEncoder;
    }
}