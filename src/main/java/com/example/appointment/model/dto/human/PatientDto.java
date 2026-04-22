package com.example.appointment.model.dto.human;

import java.time.LocalDate;

public record PatientDto(HumanDto user, String bloodType, LocalDate dateOfBirth,String allergy) {
}
