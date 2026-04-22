package com.example.appointment.service;

import com.example.appointment.error.NotFoundException;
import com.example.appointment.filter.PatientFilter;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.human.PatientDto;
import com.example.appointment.model.dto.requests.patient.PatientCreationRequest;
import com.example.appointment.model.dto.requests.patient.PatientUpdateRequest;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.repository.PatientRepo;
import com.example.appointment.service.interfaces.PatientInterface;
import com.example.appointment.specification.PatientSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PatientService implements PatientInterface {
    private final PatientRepo patientRepository;

    @Override
    public PatientDto create(PatientCreationRequest patientReq) {
        if (patientReq == null) {
            throw new IllegalArgumentException("Patient must not be null");
        }
        if (patientReq.email() != null && patientRepository.existsByEmail(patientReq.email())) {
            throw new IllegalArgumentException("Patient with this email already exists");
        }

        Patient patient=new Patient(patientReq.fName(),patientReq.lName(), patientReq.email(), patientReq.email(),patientReq.gender(),
                patientReq.bloodType(), patientReq.dateOfBirth(), patientReq.allergy());
        return ToDTo.PatientToDto(patientRepository.save(patient));
    }

    @Override
    public PatientDto update(Long id, PatientUpdateRequest patientReq) {
        if (patientReq == null) {
            throw new IllegalArgumentException("Patient must not be null");
        }

        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + id));

        if (patientReq.email() != null
                && !patientReq.email().equalsIgnoreCase(existing.getEmail())
                && patientRepository.existsByEmail(patientReq.email())) {
            throw new IllegalArgumentException("Patient with this email already exists");
        }

        if(patientReq.fName()!=null)existing.setFName(patientReq.fName());
        if(patientReq.lName()!=null)existing.setLName(patientReq.lName());
        if(patientReq.email()!=null)existing.setEmail(patientReq.email());
        if(patientReq.gender()!=null)existing.setGender(patientReq.gender());
        if(patientReq.phoneNumber()!=null)existing.setPhoneNumber(patientReq.phoneNumber());
        if(patientReq.bloodType()!=null)existing.setBloodType(patientReq.bloodType());
        if(patientReq.allergy()!=null)existing.setAllergy(patientReq.allergy());
        if(patientReq.dateOfBirth()!=null)existing.setDateOfBirth(patientReq.dateOfBirth());

        return ToDTo.PatientToDto( patientRepository.save(existing));
    }

    @Transactional(readOnly = true)
    @Override
    public PatientDto getById(Long id) {
        Patient patient=patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + id));
        return ToDTo.PatientToDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientDto> getAll(PatientFilter filter, Pageable pageable) {
        if (filter == null) {
            filter = new PatientFilter();
        }
        return patientRepository.findAll(PatientSpecification.withFilter(filter), pageable).map(ToDTo::PatientToDto);
    }

    @Override
    public void delete(Long id) {
        Patient patient=patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }
}
