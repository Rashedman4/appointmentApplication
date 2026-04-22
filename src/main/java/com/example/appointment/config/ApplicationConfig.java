package com.example.appointment.config;
import com.example.appointment.repository.EmployeeRepo;
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
    private final EmployeeRepo employeeRep;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> employeeRep.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }
}
