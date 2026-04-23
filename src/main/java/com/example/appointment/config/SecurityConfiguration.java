package com.example.appointment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String ROLE_RECEPTIONIST = "RECEPTIONIST";
    private static final String API_DOCTORS = "/api/doctors/**";
    private static final String API_DRUGS = "/api/drugs/**";

    private final JwtAutheticationFilter jwtAutheticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/h2-console/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api"
                        ).permitAll()

                        .requestMatchers("/api/medical-records/**", "/api/prescriptions/**").hasRole("DOCTOR")

                        .requestMatchers(
                                "/api/patients/**",
                                "/api/appointments/**",
                                "/api/v1/invoices/**",
                                "/api/receptionists/**"
                        ).hasRole(ROLE_RECEPTIONIST)

                        .requestMatchers(HttpMethod.GET, API_DOCTORS, API_DRUGS)
                        .hasAnyRole("DOCTOR", ROLE_RECEPTIONIST)

                        .requestMatchers(HttpMethod.POST, API_DRUGS).hasRole(ROLE_RECEPTIONIST)
                        .requestMatchers(HttpMethod.PUT, API_DRUGS).hasRole(ROLE_RECEPTIONIST)
                        .requestMatchers(HttpMethod.PATCH, API_DRUGS).hasRole(ROLE_RECEPTIONIST)
                        .requestMatchers(HttpMethod.DELETE, API_DRUGS).hasRole(ROLE_RECEPTIONIST)

                        .requestMatchers(HttpMethod.PUT, API_DOCTORS).hasRole(ROLE_RECEPTIONIST)
                        .requestMatchers(HttpMethod.DELETE, API_DOCTORS).hasRole(ROLE_RECEPTIONIST)

                        .anyRequest().permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAutheticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}