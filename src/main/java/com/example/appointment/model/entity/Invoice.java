package com.example.appointment.model.entity;

import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.model.enums.PaymentMethod;
import com.example.appointment.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invoice extends BaseEntity{
    @Column(nullable = false)
    private BigDecimal totalAmount;
    @Column(nullable = false)
    private BigDecimal doctorFee;
    @Column
    private double discount;
    @Column
    private LocalDate issueDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id",nullable = false)

    private Doctor doctor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id",nullable = false)
    private Appointment appointment;

    public Invoice(BigDecimal totalAmount, BigDecimal doctorFee, double discount,
                   PaymentStatus paymentStatus, PaymentMethod paymentMethod,LocalDate date,Doctor doctor, Patient patient, Appointment appointment){


        setTotalAmount(totalAmount);
        setDoctorFee(doctorFee);
        setDiscount(discount);
        setPaymentStatus(paymentStatus);
        setPaymentMethod(paymentMethod);
        setIssueDate(date);
        setDoctor(doctor);
        setAppointment(appointment);
        setPatient(patient);
    }


}
