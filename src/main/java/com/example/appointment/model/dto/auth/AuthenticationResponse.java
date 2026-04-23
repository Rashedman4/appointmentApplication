package com.example.appointment.model.dto.auth;

import com.example.appointment.model.enums.EmployeeRole;

public record AuthenticationResponse(
        Long id,
        String username,
        EmployeeRole role,
        String token
) {
}
