package com.example.appointment.specification;

import com.example.appointment.filter.InvoiceFilter;
import com.example.appointment.model.entity.Invoice;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecification {
    public static Specification<Invoice> withFilter(InvoiceFilter filter) {
        return Specification.where(hasPatientId(filter.getPatientId()))
                .and(hasAppointmentId(filter.getAppointmentId()))
                .and(hasPaymentStatus(filter.getPaymentStatus()))
                .and(hasPaymentMethod(filter.getPaymentMethod()))
                .and(totalGreaterThanOrEqual(filter.getMinTotal()))
                .and(totalLessThanOrEqual(filter.getMaxTotal()))
    ;
    }

    private static Specification<Invoice> hasPatientId(Long patientId) {
        return (root, query, cb) -> patientId == null ? null : cb.equal(root.get("appointment").get("patient").get("id"), patientId);
    }

    private static Specification<Invoice> hasAppointmentId(Long appointmentId) {
        return (root, query, cb) -> appointmentId == null ? null : cb.equal(root.get("appointment").get("id"), appointmentId);
    }

    private static Specification<Invoice> hasPaymentStatus(com.example.appointment.model.enums.PaymentStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("paymentStatus"), status);
    }

    private static Specification<Invoice> hasPaymentMethod(com.example.appointment.model.enums.PaymentMethod method) {
        return (root, query, cb) -> method == null ? null : cb.equal(root.get("paymentMethod"), method);
    }

    private static Specification<Invoice> totalGreaterThanOrEqual(java.math.BigDecimal minTotal) {
        return (root, query, cb) -> minTotal == null ? null : cb.greaterThanOrEqualTo(root.get("totalAmount"), minTotal);
    }

    private static Specification<Invoice> totalLessThanOrEqual(java.math.BigDecimal maxTotal) {
        return (root, query, cb) -> maxTotal == null ? null : cb.lessThanOrEqualTo(root.get("totalAmount"), maxTotal);
    }

    private static Specification<Invoice> issueDateGreaterThanOrEqual(java.time.LocalDate from) {
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("appointmentDate"), from);
    }

    private static Specification<Invoice> issueDateLessThanOrEqual(java.time.LocalDate to) {
        return (root, query, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("appointmentDate"), to);
    }
}
