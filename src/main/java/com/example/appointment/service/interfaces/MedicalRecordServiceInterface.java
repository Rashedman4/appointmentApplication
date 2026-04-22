package com.example.appointment.service.interfaces;

import com.example.appointment.model.dto.MedicalRecordDto;
import com.example.appointment.model.dto.requests.medicalRecord.MedicalRecordCreationRequest;
import com.example.appointment.model.dto.requests.medicalRecord.MedicalRecordUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalRecordServiceInterface {
    MedicalRecordDto create(MedicalRecordCreationRequest MedicalRecordReq);
    MedicalRecordDto update(Long id, MedicalRecordUpdateRequest medicalRecordReq);
    MedicalRecordDto getById(Long id);
    Page<MedicalRecordDto> getAll(Pageable pageable);
    void delete (Long id);
}
