package com.example.appointment.model.dto.requests.medicalRecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MedicalRecordCreationRequest(
        @NotBlank String diagnosis,
        String note,
        @NotNull Long patientId,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate issueDate

        ) {}
