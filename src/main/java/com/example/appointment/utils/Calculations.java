package com.example.appointment.utils;

import com.example.appointment.model.entity.Prescription;

import java.math.BigDecimal;

public class Calculations {
    public static BigDecimal calculatePrescriptionAmount(Prescription prescription) {
        if (prescription == null || prescription.getDrugs() == null) {
            return BigDecimal.ZERO;
        }
        return prescription.getDrugs().stream()
                .filter(intake -> intake.getDrug() != null && intake.getDrug().getPrice() != null)
                .map(intake -> intake.getDrug().getPrice().multiply(BigDecimal.valueOf(intake.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
