package com.example.appointment.repository;

import com.example.appointment.model.entity.Appointment;
import com.example.appointment.model.entity.human.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> , JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
}
