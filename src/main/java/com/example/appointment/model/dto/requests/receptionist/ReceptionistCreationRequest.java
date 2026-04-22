package com.example.appointment.model.dto.requests.receptionist;

import com.example.appointment.model.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReceptionistCreationRequest(
        @NotBlank String fName, @NotBlank String lName,
        @Email @NotBlank String email, @NotBlank @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits") String phoneNumber,
        Gender gender, @NotBlank String username, @Size(min = 8) String password,
        @NotBlank String deskNumber
) {}