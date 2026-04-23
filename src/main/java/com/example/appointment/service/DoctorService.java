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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DoctorService implements DoctorServiceInterface {
    private final DoctorRepo doctorRepo;
    private final ReceptionistRepo receptionistRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DoctorDto create(DoctorCreationRequest doctor) {
        validateDoctorUniqueness(doctor.username(), doctor.email(), doctor.licenseNumber(), null);

        Doctor savedDoctor = doctorRepo.save(new Doctor(
                doctor.fName(), doctor.lName(), doctor.email(), doctor.phoneNumber(),
                doctor.gender(), doctor.username(), passwordEncoder.encode(doctor.password()),
                doctor.specialization(), doctor.licenseNumber(), doctor.startWorkingHour(), doctor.endingWorkingHour()
        ));

        return ToDTo.doctorToDto(savedDoctor);
    }

    @Override
    public DoctorDto update(Long id, DoctorUpdateRequest updateReq) {
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No Doctor Found"));

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

        return ToDTo.doctorToDto(doctorRepo.save(doctor));
    }

    @Override
    public DoctorDto getByID(Long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException("No Doctor Found"));
        return ToDTo.doctorToDto(doctor);
    }

    @Override
    public Page<DoctorDto> getAll(DoctorFilter filter, Pageable pageable) {
        return doctorRepo.findAll(DoctorSpecification.withFilter(filter), pageable).map(ToDTo::doctorToDto);
    }

    @Override
    public void delete(Long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException("No Doctor Found"));
        doctorRepo.delete(doctor);
    }

    private void validateDoctorUniqueness(String username, String email, String licenseNumber, Long currentDoctorId) {
        if (username != null) {
            boolean usernameTakenByDoctor = currentDoctorId == null
                    ? doctorRepo.existsByUsername(username)
                    : doctorRepo.existsByUsernameAndIdNot(username, currentDoctorId);
            if (usernameTakenByDoctor || receptionistRepo.existsByUsername(username)) {
                throw new BadRequestException("Username is already taken");
            }
        }

        if (email != null) {
            boolean emailTakenByDoctor = currentDoctorId == null
                    ? doctorRepo.existsByEmail(email)
                    : doctorRepo.existsByEmailIgnoreCaseAndIdNot(email, currentDoctorId);
            if (emailTakenByDoctor || receptionistRepo.existsByEmail(email)) {
                throw new BadRequestException("Email is already in use");
            }
        }

        if (licenseNumber != null) {
            boolean licenseTaken = currentDoctorId == null
                    ? doctorRepo.existsByLicenseNumber(licenseNumber)
                    : doctorRepo.existsByLicenseNumberIgnoreCaseAndIdNot(licenseNumber, currentDoctorId);
            if (licenseTaken) {
                throw new BadRequestException("License number is already in use");
            }
        }
    }
}
