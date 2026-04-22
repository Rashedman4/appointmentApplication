package com.example.appointment.model.dto.requests.drug;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DrugUpdateRequest(
        String name, String description,
        @Positive BigDecimal price, String category,
        @Min(0) Integer quantity
) {}

