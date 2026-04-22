package com.example.appointment.service;

import com.example.appointment.config.JwtService;
import com.example.appointment.error.NotFoundException;
import com.example.appointment.filter.DoctorFilter;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.human.DoctorDto;
import com.example.appointment.model.dto.human.DoctorRegisterDto;
import com.example.appointment.model.dto.requests.doctor.DoctorCreationRequest;
import com.example.appointment.model.dto.requests.doctor.DoctorUpdateRequest;
import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Employee;
import com.example.appointment.repository.DoctorRepo;
import com.example.appointment.repository.EmployeeRepo;
import com.example.appointment.service.interfaces.DoctorServiceInterface;
import com.example.appointment.specification.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;

@RequiredArgsConstructor
@Service
@Transactional
public class DoctorService implements DoctorServiceInterface {
    private final DoctorRepo doctorRepo;
    private final EmployeeRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public DoctorRegisterDto create(DoctorCreationRequest doctor) {
        Employee createdDoctor=new Doctor(doctor.fName(), doctor.lName(), doctor.email(), doctor.phoneNumber(),
                doctor.gender(), doctor.username(), doctor.password(), doctor.specialization(), doctor.licenseNumber(),
                doctor.startWorkingHour(),doctor.endingWorkingHour());
        String jwtToken=jwtService.generateToken(createdDoctor);

        return ToDTo.doctorRegisterToDto(doctorRepo.save(new Doctor(doctor.fName(), doctor.lName(), doctor.email(), doctor.phoneNumber(),
            doctor.gender(), doctor.username(), doctor.password(), doctor.specialization(), doctor.licenseNumber(),
            doctor.startWorkingHour(),doctor.endingWorkingHour())),jwtToken);

    }

    @Override
    public DoctorDto update(Long id, DoctorUpdateRequest updateReq) {
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No Doctor Found"));

        if (updateReq.fName() != null) doctor.setFName(updateReq.fName());
        if (updateReq.lName() != null) doctor.setLName(updateReq.lName());
        if (updateReq.email() != null) doctor.setEmail(updateReq.email());
        if (updateReq.phoneNumber() != null) doctor.setPhoneNumber(updateReq.phoneNumber());
        if (updateReq.gender() != null) doctor.setGender(updateReq.gender());
        if (updateReq.username() != null) doctor.setUsername(updateReq.username());
        if (updateReq.password() != null) doctor.setPassword(updateReq.password());
        if (updateReq.specialization() != null) doctor.setSpecialization(updateReq.specialization());
        if (updateReq.licenseNumber() != null) doctor.setLicenseNumber(updateReq.licenseNumber());
        if (updateReq.startWorkingHour() != null) doctor.setStartWorkingHour(updateReq.startWorkingHour());
        if (updateReq.endingWorkingHour() != null) doctor.setEndingWorkingHour(updateReq.endingWorkingHour());

        return ToDTo.doctorToDto(doctorRepo.save(doctor));
    }
    @Override
    public DoctorDto getByID(Long id) {
        Doctor doctor=doctorRepo.findById(id).orElseThrow(()->new NotFoundException("No Doctor Found"));
        return ToDTo.doctorToDto(doctor);
    }

    @Override
    public Page<DoctorDto> getAll(DoctorFilter filter, Pageable pageable) {
        return doctorRepo.findAll(DoctorSpecification.withFilter(filter), pageable).map(ToDTo::doctorToDto);
    }

    @Override
    public void delete(Long id) {
        Doctor doctor=doctorRepo.findById(id).orElseThrow(()->new NotFoundException("No Doctor Found"));
        doctorRepo.delete(doctor);
    }
}
