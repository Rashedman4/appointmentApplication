package com.example.appointment.model.dto.requests.patient;


import com.example.appointment.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientUpdateRequest(
        String fName, String lName,
        @Email String email, @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits") String phoneNumber,
        Gender gender, String bloodType,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") @Past LocalDate dateOfBirth,
        String allergy
) {}
