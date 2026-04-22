package com.example.appointment.model.dto.requests.drug;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DrugCreationRequest(
        @NotBlank String name, String description,
        @Positive BigDecimal price, @NotBlank String category,
        @Min(0) int quantity
        ) {}

