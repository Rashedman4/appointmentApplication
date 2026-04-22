package com.example.appointment.filter;

import com.example.appointment.model.enums.PaymentMethod;
import com.example.appointment.model.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceFilter {
    private Long patientId;
    private Long appointmentId;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private BigDecimal minTotal;
    private BigDecimal maxTotal;
    private LocalDate issueDate;
}
