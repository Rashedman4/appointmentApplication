package com.example.appointment.model.dto;

import com.example.appointment.model.enums.PaymentMethod;
import com.example.appointment.model.enums.PaymentStatus;

import java.math.BigDecimal;

public record InvoiceDto(Long invoiceID, BigDecimal totalAmount, BigDecimal doctorFee, double discount
, PaymentStatus paymentStatus, PaymentMethod paymentMethod, Long patient_id, Long doctor_id) {
}
