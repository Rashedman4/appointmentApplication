package com.example.appointment.repository;

import com.example.appointment.model.entity.human.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByLicenseNumberIgnoreCaseAndIdNot(String licenseNumber, Long id);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByEmail(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    Optional<Doctor> findByUsername(String username);
}
