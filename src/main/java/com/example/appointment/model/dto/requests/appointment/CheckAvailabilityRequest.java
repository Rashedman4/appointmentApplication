package com.example.appointment.model.dto.requests.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;
import java.time.LocalTime;

public record CheckAvailabilityRequest(@NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                       @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalTime start,
                                       @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalTime end) {
}
