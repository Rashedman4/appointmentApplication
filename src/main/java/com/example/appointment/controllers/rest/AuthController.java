package com.example.appointment.controllers.rest;

import com.example.appointment.model.dto.auth.AuthenticationRequest;
import com.example.appointment.model.dto.auth.AuthenticationResponse;
import com.example.appointment.model.dto.requests.doctor.DoctorCreationRequest;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistCreationRequest;
import com.example.appointment.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/doctor")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registerDoctor(@Valid @RequestBody DoctorCreationRequest request) {
        return authService.registerDoctor(request);
    }

    @PostMapping("/register/receptionist")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registerReceptionist(@Valid @RequestBody ReceptionistCreationRequest request) {
        return authService.registerReceptionist(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);
    }
}
