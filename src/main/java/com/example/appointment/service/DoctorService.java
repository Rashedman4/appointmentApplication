package com.example.appointment.service;

import com.example.appointment.error.BadRequestException;
import com.example.appointment.error.NotFoundException;
import com.example.appointment.filter.DoctorFilter;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.human.DoctorDto;
import com.example.appointment.model.dto.human.DoctorRegisterDto;
import com.example.appointment.model.dto.requests.doctor.DoctorCreationRequest;
import com.example.appointment.model.dto.requests.doctor.DoctorUpdateRequest;
import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.repository.DoctorRepo;
import com.example.appointment.repository.ReceptionistRepo;
import com.example.appointment.service.interfaces.DoctorServiceInterface;
import com.example.appointment.specification.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class DoctorService implements DoctorServiceInterface {
    private final DoctorRepo doctorRepo;
    private final ReceptionistRepo receptionistRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DoctorDto create(DoctorCreationRequest doctor) {
        log.info("Attempting to create a new doctor with email: {}", doctor.email());
        validateDoctorUniqueness(doctor.username(), doctor.email(), doctor.licenseNumber(), null);

        Doctor savedDoctor = doctorRepo.save(new Doctor(
                doctor.fName(), doctor.lName(), doctor.email(), doctor.phoneNumber(),
                doctor.gender(), doctor.username(), passwordEncoder.encode(doctor.password()),
                doctor.specialization(), doctor.licenseNumber(), doctor.startWorkingHour(), doctor.endingWorkingHour()
        ));
        log.info("Successfully created doctor with ID: {}", savedDoctor.getId());
        return ToDTo.doctorToDto(savedDoctor);
    }

    @Override
    public DoctorDto update(Long id, DoctorUpdateRequest updateReq) {
        log.info("Updating doctor profile for ID: {}", id);
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed: Doctor with ID {} not found", id);
                    return new NotFoundException("No Doctor Found");
                });

        validateDoctorUniqueness(updateReq.username(), updateReq.email(), updateReq.licenseNumber(), id);

        if (updateReq.fName() != null) doctor.setFName(updateReq.fName());
        if (updateReq.lName() != null) doctor.setLName(updateReq.lName());
        if (updateReq.email() != null) doctor.setEmail(updateReq.email());
        if (updateReq.phoneNumber() != null) doctor.setPhoneNumber(updateReq.phoneNumber());
        if (updateReq.gender() != null) doctor.setGender(updateReq.gender());
        if (updateReq.username() != null) doctor.setUsername(updateReq.username());
        if (updateReq.password() != null) doctor.setPassword(passwordEncoder.encode(updateReq.password()));
        if (updateReq.specialization() != null) doctor.setSpecialization(updateReq.specialization());
        if (updateReq.licenseNumber() != null) doctor.setLicenseNumber(updateReq.licenseNumber());
        if (updateReq.startWorkingHour() != null) doctor.setStartWorkingHour(updateReq.startWorkingHour());
        if (updateReq.endingWorkingHour() != null) doctor.setEndingWorkingHour(updateReq.endingWorkingHour());
        log.info("Successfully updated doctor with ID: {}", id);
        return ToDTo.doctorToDto(doctorRepo.save(doctor));
    }

    @Override
    public DoctorDto getByID(Long id) {
        return doctorRepo.findById(id)
                .map(ToDTo::doctorToDto)
                .orElseThrow(() -> {
                    log.warn("Search failed: No doctor found with ID: {}", id);
                    return new NotFoundException("No Doctor Found");
                });
    }

    @Override
    public Page<DoctorDto> getAll(DoctorFilter filter, Pageable pageable) {
        return doctorRepo.findAll(DoctorSpecification.withFilter(filter), pageable).map(ToDTo::doctorToDto);
    }

    @Override
    public void delete(Long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> {
            log.error("Delete failed: Doctor ID {} does not exist", id);
            return new NotFoundException("No Doctor Found");
        });
        doctorRepo.delete(doctor);
        log.info("Deleted doctor with ID: {}", id);
    }

    private void validateDoctorUniqueness(String username, String email, String licenseNumber, Long currentDoctorId) {
        if (username != null) {
            boolean usernameTakenByDoctor = currentDoctorId == null
                    ? doctorRepo.existsByUsername(username)
                    : doctorRepo.existsByUsernameAndIdNot(username, currentDoctorId);
            if (usernameTakenByDoctor || receptionistRepo.existsByUsername(username)) {
                log.warn("Validation failed: Username '{}' is already taken", username);
                throw new BadRequestException("Username is already taken");
            }
        }

        if (email != null) {
            boolean emailTakenByDoctor = currentDoctorId == null
                    ? doctorRepo.existsByEmail(email)
                    : doctorRepo.existsByEmailIgnoreCaseAndIdNot(email, currentDoctorId);
            if (emailTakenByDoctor || receptionistRepo.existsByEmail(email)) {
                log.warn("Validation failed: Email '{}' is already taken", email);
                throw new BadRequestException("Email is already in use");
            }
        }

        if (licenseNumber != null) {
            boolean licenseTaken = currentDoctorId == null
                    ? doctorRepo.existsByLicenseNumber(licenseNumber)
                    : doctorRepo.existsByLicenseNumberIgnoreCaseAndIdNot(licenseNumber, currentDoctorId);
            if (licenseTaken) {
                log.warn("Validation failed: License Number '{}' is already taken", licenseNumber);
                throw new BadRequestException("License number is already in use");
            }
        }
    }
}
