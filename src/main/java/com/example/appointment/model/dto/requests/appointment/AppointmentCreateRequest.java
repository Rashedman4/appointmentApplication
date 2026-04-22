package com.example.appointment.model.dto.requests.appointment;

import com.example.appointment.model.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentCreateRequest(
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") @FutureOrPresent LocalDate appointmentDate,
        @NotNull @JsonFormat(pattern = "HH:MM") LocalTime appointmentStart,
        @NotNull @JsonFormat(pattern = "HH:MM") LocalTime appointmentEnd,
        @NotNull AppointmentStatus status,
        String note,
        @NotNull Long patientId,
        @NotNull Long doctorId,
        @NotNull Long createdById
) {}
