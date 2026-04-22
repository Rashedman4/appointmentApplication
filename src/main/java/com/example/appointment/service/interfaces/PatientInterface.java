package com.example.appointment.service.interfaces;

import com.example.appointment.filter.PatientFilter;
import com.example.appointment.model.dto.human.PatientDto;
import com.example.appointment.model.dto.requests.patient.PatientCreationRequest;
import com.example.appointment.model.dto.requests.patient.PatientUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientInterface {
    PatientDto create(PatientCreationRequest patient);
    PatientDto update(Long id, PatientUpdateRequest patient);
    PatientDto getById(Long id);
    Page<PatientDto> getAll(PatientFilter filter, Pageable pageable);


    void delete (Long id);
}
