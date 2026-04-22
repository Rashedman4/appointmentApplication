package com.example.appointment.service;

import com.example.appointment.error.NotFoundException;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.human.ReceptionistDto;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistCreationRequest;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistUpdateRequest;
import com.example.appointment.model.entity.human.Receptionist;
import com.example.appointment.model.enums.EmployeeRole;
import com.example.appointment.repository.ReceptionistRepo;
import com.example.appointment.service.interfaces.ReceptionistServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceptionistService implements ReceptionistServiceInterface {
    private final ReceptionistRepo receptionistRepository;

    @Override
    public ReceptionistDto create(ReceptionistCreationRequest receptionistReq) {
        if (receptionistReq == null) {
            throw new IllegalArgumentException("Receptionist must not be null");
        }
        if (receptionistReq.username() == null || receptionistReq.username().isBlank()) {
            throw new IllegalArgumentException("Username must not be blank");
        }
        if (receptionistRepository.existsByUsername(receptionistReq.username())) {
            throw new IllegalArgumentException("Receptionist with this username already exists");
        }
        Receptionist receptionist=new Receptionist(receptionistReq.fName(),receptionistReq.lName(), receptionistReq.email(), receptionistReq.phoneNumber(), receptionistReq.gender(),
                receptionistReq.username(), receptionistReq.password(), receptionistReq.deskNumber());
        return ToDTo.receptionistToDto(receptionistRepository.save(receptionist));
    }

    @Override
    public ReceptionistDto update(Long id, ReceptionistUpdateRequest receptionistReq) {
        if (receptionistReq == null) {
            throw new IllegalArgumentException("Receptionist must not be null");
        }

        Receptionist existing = receptionistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receptionist not found with id: " + id));

        if (receptionistReq.username() != null
                && !receptionistReq.username().equals(existing.getUsername())
                && receptionistRepository.existsByUsername(receptionistReq.username())) {
            throw new IllegalArgumentException("Receptionist with this username already exists");
        }

        if(receptionistReq.fName()!=null)existing.setFName(receptionistReq.fName());
        if(receptionistReq.lName()!=null)existing.setLName(receptionistReq.lName());
        if(receptionistReq.email()!=null)existing.setEmail(receptionistReq.email());
        if(receptionistReq.gender()!=null)existing.setGender(receptionistReq.gender());
        if(receptionistReq.phoneNumber()!=null)existing.setPhoneNumber(receptionistReq.phoneNumber());
        if(receptionistReq.username()!=null)existing.setUsername(receptionistReq.username());
        if(receptionistReq.password()!=null)existing.setPassword(receptionistReq.password());
        if(receptionistReq.deskNumber()!=null)existing.setDeskNumber(receptionistReq.deskNumber());

        return ToDTo.receptionistToDto(receptionistRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public ReceptionistDto getById(Long id) {
        return ToDTo.receptionistToDto(receptionistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receptionist not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReceptionistDto> getAll(Pageable pageable) {
        return receptionistRepository.findAll(pageable).map(ToDTo::receptionistToDto);
    }

    @Override
    public void delete(Long id) {
        Receptionist receptionist=receptionistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receptionist not found with id: " + id));
        receptionistRepository.delete(receptionist);
    }
}
