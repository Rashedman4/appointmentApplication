package com.example.appointment.model.dto;

import java.time.LocalDate;

public record MedicalRecordDto (Long medicalRecordID, Long patientId, String diagnosis,
                               String note, LocalDate issueDate){
}
