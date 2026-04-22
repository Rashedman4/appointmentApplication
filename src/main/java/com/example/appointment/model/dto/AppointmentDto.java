package com.example.appointment.model.dto;

import com.example.appointment.model.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentDto(Long appointmentId, LocalDate appointmentDate, LocalTime appointmentStart, LocalTime appointmentEnd,
                             AppointmentStatus status, String note, Long patientId, Long doctorId, long createdBy) {
}
