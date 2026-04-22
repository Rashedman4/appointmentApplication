package com.example.appointment.repository;

import com.example.appointment.model.entity.human.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionistRepo extends JpaRepository<Receptionist,Long> {
    boolean existsByUsername(String username);

}
