package com.example.appointment.model.dto.requests.patient;

import com.example.appointment.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientCreationRequest(
        @NotBlank String fName, @NotBlank String lName,
        @Email @NotBlank String email, @NotBlank @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits") String phoneNumber,
        Gender gender, @NotBlank String bloodType,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") @Past LocalDate dateOfBirth,
        String allergy
) {}
