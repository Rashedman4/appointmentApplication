package com.example.appointment.model.dto.requests.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DateRequest(@NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date) {
}
