package com.example.appointment.service.interfaces;

import com.example.appointment.filter.DoctorFilter;
import com.example.appointment.model.dto.human.DoctorDto;
import com.example.appointment.model.dto.human.DoctorRegisterDto;
import com.example.appointment.model.dto.requests.doctor.DoctorCreationRequest;
import com.example.appointment.model.dto.requests.doctor.DoctorUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorServiceInterface {
    DoctorRegisterDto create(DoctorCreationRequest doctor);
    DoctorDto update(Long id, DoctorUpdateRequest doctor);
    DoctorDto getByID(Long id);
    Page<DoctorDto> getAll(DoctorFilter filter, Pageable pageable);
    void delete (Long id);
}
