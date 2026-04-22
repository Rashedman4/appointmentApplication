package com.example.appointment.model.entity;

import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.model.entity.human.Receptionist;
import com.example.appointment.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Appointment extends BaseEntity{

    @Column(nullable = false)
    private LocalDate appointmentDate;
    @Column(nullable = false)
    private LocalTime appointmentStart;
    @Column(nullable = false)
    private LocalTime appointmentEnd;
    @Column(nullable = false)
    private AppointmentStatus status;
    @Column(nullable = false)
    private String note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id",nullable = false)
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id",nullable = false)
    private Receptionist createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    @Setter
    private Prescription prescription;




}
