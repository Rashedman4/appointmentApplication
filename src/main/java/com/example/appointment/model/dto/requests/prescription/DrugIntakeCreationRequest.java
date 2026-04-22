package com.example.appointment.model.dto.requests.prescription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DrugIntakeCreationRequest(
        @NotNull Long drugId, @NotBlank String dosage,
        String instruction, @Positive Integer quantity
) {}
