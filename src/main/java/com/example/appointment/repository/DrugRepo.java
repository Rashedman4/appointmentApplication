package com.example.appointment.repository;

import com.example.appointment.model.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DrugRepo extends JpaRepository<Drug,Long>, JpaSpecificationExecutor<Drug> {
    Optional<Drug> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
