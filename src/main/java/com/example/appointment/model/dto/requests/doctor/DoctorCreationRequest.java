package com.example.appointment.model.dto.requests.doctor;

import com.example.appointment.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalTime;

public record DoctorCreationRequest(
        @NotBlank String fName, @NotBlank String lName,
        @Email @NotBlank String email, @NotBlank @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits") String phoneNumber,
        Gender gender, @NotBlank String username, @Size(min = 8) String password,
        @NotBlank String specialization, @NotBlank String licenseNumber,
        @NotNull @JsonFormat(pattern = "HH:MM") LocalTime startWorkingHour, @NotNull @JsonFormat(pattern = "HH:MM") LocalTime endingWorkingHour
) {}
