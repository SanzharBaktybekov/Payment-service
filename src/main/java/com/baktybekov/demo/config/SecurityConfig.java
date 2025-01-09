package com.baktybekov.demo.config;

import com.baktybekov.demo.exception.UserAlreadyExistsException;
import com.baktybekov.demo.persistence.UserRepository;
import com.baktybekov.demo.properties.SampleUserDataProperties;
import com.baktybekov.demo.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableConfigurationProperties(SampleUserDataProperties.class)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/register").permitAll();
            auth.anyRequest().authenticated();
        }).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserService(userRepository, passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner(
            UserDetailsService userDetailsService,
            SampleUserDataProperties properties
            ) {
        var service = (UserService) userDetailsService;

        return args -> {
            try {
                service.registerNewUser(properties.toUser());
            } catch (UserAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        };
    }
}