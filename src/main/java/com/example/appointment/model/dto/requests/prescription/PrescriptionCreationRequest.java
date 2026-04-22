package com.example.appointment.model.dto.requests.prescription;

import jakarta.validation.constraints.NotNull;

public record PrescriptionCreationRequest(@NotNull Long appointmentId) {}