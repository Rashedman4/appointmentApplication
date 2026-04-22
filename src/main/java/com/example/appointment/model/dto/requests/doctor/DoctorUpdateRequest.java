package com.example.appointment.model.dto.requests.doctor;

import com.example.appointment.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record DoctorUpdateRequest(
        String fName, String lName,
        @Email String email, @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits") String phoneNumber,
        Gender gender, String username, @Size(min = 8) String password,
        String specialization, String licenseNumber,
        @JsonFormat(pattern = "HH:MM") LocalTime startWorkingHour, @JsonFormat(pattern = "HH:MM") LocalTime endingWorkingHour
) {}