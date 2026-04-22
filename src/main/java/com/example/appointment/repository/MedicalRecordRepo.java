package com.example.appointment.repository;

import com.example.appointment.model.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long> {
}
