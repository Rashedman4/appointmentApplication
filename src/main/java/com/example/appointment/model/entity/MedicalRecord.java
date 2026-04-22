package com.example.appointment.model.entity;

import com.example.appointment.model.entity.human.Patient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MedicalRecord extends BaseEntity {
    @Column(nullable = false)
    private String diagnosis;
    @Column(nullable = false)
    private String note;
    @Column(nullable = false)
    private LocalDate issueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;

    public MedicalRecord(String diagnosis, String note, Patient patient, LocalDate date){
        setDiagnosis(diagnosis);
        setNote(note);
        setPatient(patient);
        setIssueDate(date);
    }
}
