package com.example.appointment.service;

import com.example.appointment.error.BadRequestException;
import com.example.appointment.error.NotFoundException;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.human.ReceptionistDto;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistCreationRequest;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistUpdateRequest;
import com.example.appointment.model.entity.human.Receptionist;
import com.example.appointment.repository.DoctorRepo;
import com.example.appointment.repository.ReceptionistRepo;
import com.example.appointment.service.interfaces.ReceptionistServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceptionistService implements ReceptionistServiceInterface {
    private final ReceptionistRepo receptionistRepository;
    private final DoctorRepo doctorRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ReceptionistDto create(ReceptionistCreationRequest receptionistReq) {
        validateReceptionistUniqueness(receptionistReq.username(), receptionistReq.email(), null);

        Receptionist receptionist = new Receptionist(
                receptionistReq.fName(), receptionistReq.lName(), receptionistReq.email(), receptionistReq.phoneNumber(), receptionistReq.gender(),
                receptionistReq.username(), passwordEncoder.encode(receptionistReq.password()), receptionistReq.deskNumber()
        );
        return ToDTo.receptionistToDto(receptionistRepository.save(receptionist));
    }

    @Override
    public ReceptionistDto update(Long id, ReceptionistUpdateRequest receptionistReq) {
        Receptionist existing = receptionistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receptionist not found with id: " + id));

        validateReceptionistUniqueness(receptionistReq.username(), receptionistReq.email(), id);

        if (receptionistReq.fName() != null) existing.setFName(receptionistReq.fName());
        if (receptionistReq.lName() != null) existing.setLName(receptionistReq.lName());
        if (receptionistReq.email() != null) existing.setEmail(receptionistReq.email());
        if (receptionistReq.gender() != null) existing.setGender(receptionistReq.gender());
        if (receptionistReq.phoneNumber() != null) existing.setPhoneNumber(receptionistReq.phoneNumber());
        if (receptionistReq.username() != null) existing.setUsername(receptionistReq.username());
        if (receptionistReq.password() != null) existing.setPassword(passwordEncoder.encode(receptionistReq.password()));
        if (receptionistReq.deskNumber() != null) existing.setDeskNumber(receptionistReq.deskNumber());

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
        Receptionist receptionist = receptionistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receptionist not found with id: " + id));
        receptionistRepository.delete(receptionist);
    }

    private void validateReceptionistUniqueness(String username, String email, Long currentReceptionistId) {
        if (username != null) {
            boolean usernameTakenByReceptionist = currentReceptionistId == null
                    ? receptionistRepository.existsByUsername(username)
                    : receptionistRepository.existsByUsernameAndIdNot(username, currentReceptionistId);
            if (usernameTakenByReceptionist || doctorRepo.existsByUsername(username)) {
                throw new BadRequestException("Username is already taken");
            }
        }

        if (email != null) {
            boolean emailTakenByReceptionist = currentReceptionistId == null
                    ? receptionistRepository.existsByEmail(email)
                    : receptionistRepository.existsByEmailIgnoreCaseAndIdNot(email, currentReceptionistId);
            if (emailTakenByReceptionist || doctorRepo.existsByEmail(email)) {
                throw new BadRequestException("Email is already in use");
            }
        }
    }
}
