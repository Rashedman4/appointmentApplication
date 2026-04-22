package com.example.appointment.model.dto.human;

import java.time.LocalTime;

public record DoctorRegisterDto(HumanDto user, String specialization, LocalTime startWorkingHour, LocalTime endWorkingHour, String token) {
}
