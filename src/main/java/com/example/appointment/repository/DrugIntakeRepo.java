package com.example.appointment.repository;

import com.example.appointment.model.entity.DrugIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugIntakeRepo extends JpaRepository<DrugIntake,Long> {
}
