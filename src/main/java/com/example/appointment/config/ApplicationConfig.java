package com.example.appointment.config;

import com.example.appointment.repository.DoctorRepo;
import com.example.appointment.repository.ReceptionistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final DoctorRepo doctorRepo;
    private final ReceptionistRepo receptionistRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> doctorRepo.findByUsername(username)
                .map(user -> (org.springframework.security.core.userdetails.UserDetails) user)
                .or(() -> receptionistRepo.findByUsername(username)
                        .map(user -> (org.springframework.security.core.userdetails.UserDetails) user))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
