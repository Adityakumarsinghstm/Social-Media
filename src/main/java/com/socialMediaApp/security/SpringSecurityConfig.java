package com.socialMediaApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity security) throws Exception {
//        security.authorizeHttpRequests(
//                auth->auth.anyRequest().authenticated()
//        );
//        security.httpBasic(Customizer.withDefaults());
//        security.csrf().disable();
//        return security.build();

        security
                .csrf(csrf -> csrf.disable())  // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/jpa/users").permitAll()  // Allow access to /jpa/users
                        .anyRequest().authenticated())  // Other requests require authentication
                .httpBasic(Customizer.withDefaults());

        return security.build();
    }
}
