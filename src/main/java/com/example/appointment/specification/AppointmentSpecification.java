package com.example.appointment.specification;

import com.example.appointment.filter.AppointmentFilter;
import com.example.appointment.model.entity.Appointment;
import org.springframework.data.jpa.domain.Specification;

public class AppointmentSpecification {
    public static Specification<Appointment> withFilter(AppointmentFilter filter) {
        return Specification.where(hasPatientId(filter.getPatientId()))
                .and(hasDoctorId(filter.getDoctorId()))
                .and(hasStatus(filter.getStatus()))
                .and(dateGreaterThanOrEqual(filter.getDateFrom()))
                .and(dateLessThanOrEqual(filter.getDateTo()))
                .and(startGreaterThanOrEqual(filter.getTimeStart()))
                .and(startLessThanOrEqual(filter.getTimeEnd()));
    }

    private static Specification<Appointment> hasPatientId(Long patientId) {
        return (root, query, cb) -> patientId == null ? null : cb.equal(root.get("patient").get("id"), patientId);
    }

    private static Specification<Appointment> hasDoctorId(Long doctorId) {
        return (root, query, cb) -> doctorId == null ? null : cb.equal(root.get("doctor").get("id"), doctorId);
    }

    private static Specification<Appointment> hasStatus(com.example.appointment.model.enums.AppointmentStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    private static Specification<Appointment> dateGreaterThanOrEqual(java.time.LocalDate from) {
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("appointmentDate"), from);
    }

    private static Specification<Appointment> dateLessThanOrEqual(java.time.LocalDate to) {
        return (root, query, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("appointmentDate"), to);
    }

    private static Specification<Appointment> startGreaterThanOrEqual(java.time.LocalTime from) {
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("appointmentStart"), from);
    }

    private static Specification<Appointment> startLessThanOrEqual(java.time.LocalTime to) {
        return (root, query, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("appointmentStart"), to);
    }
}
