package com.example.appointment.model.dto;

public record DrugIntakeDto(Long DrugIntakeId, DrugDto drug, String dosage, String instruction, Integer quantity) {
}
