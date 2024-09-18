package com.ambulancebooking.project.Config;

import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.jwt.JWTauthenticationEntryPoint;
import com.ambulancebooking.project.jwt.JwtAuthenticationFilter;
import com.ambulancebooking.project.jwt.JwtHelper;
import com.ambulancebooking.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTauthenticationEntryPoint jwTauthenticationEntryPoint;
    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwTauthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/signup").permitAll()  // Allow signup without authentication
                            .requestMatchers(HttpMethod.POST, "/login").permitAll()  // Allow login without authentication
                            .anyRequest().authenticated();  // Require authentication for all other endpoints
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<UserEntity> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                return org.springframework.security.core.userdetails.User.builder()
                        .username(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}