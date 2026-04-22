package com.example.appointment.repository;

import com.example.appointment.model.entity.Appointment;
import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long>, JpaSpecificationExecutor<Appointment> {
    List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDate appointmentDate);
    List<Appointment> findByPatient(Patient patient);
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentStartLessThanAndAppointmentEndGreaterThan(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentEnd,
            LocalTime appointmentStart
    );
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentStartLessThanAndAppointmentEndGreaterThanAndIdNot(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentEnd,
            LocalTime appointmentStart,
            Long id
    );
    List<Appointment> findByStatus(AppointmentStatus status);

}
