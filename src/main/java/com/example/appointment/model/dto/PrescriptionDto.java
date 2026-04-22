package com.example.appointment.model.dto;

import java.util.List;

public record PrescriptionDto(Long prescriptionId, List<DrugIntakeDto> drugsIntake, Long appointmentId) {
}
