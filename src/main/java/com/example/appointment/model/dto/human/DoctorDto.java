package com.example.appointment.model.dto.human;

import java.time.LocalTime;

public record DoctorDto(HumanDto user, String specialization, LocalTime startWorkingHour,LocalTime endWorkingHour) {
}
