package com.example.appointment.repository;

import com.example.appointment.model.entity.human.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long>, JpaSpecificationExecutor<Doctor> {
    boolean existsByLicenseNumber(String licenseNumber);
}
