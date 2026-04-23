package com.example.appointment.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password
) {
}
