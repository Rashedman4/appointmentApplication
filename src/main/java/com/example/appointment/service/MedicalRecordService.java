package com.example.appointment.service;

import com.example.appointment.error.NotFoundException;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.MedicalRecordDto;
import com.example.appointment.model.dto.requests.medicalRecord.MedicalRecordCreationRequest;
import com.example.appointment.model.dto.requests.medicalRecord.MedicalRecordUpdateRequest;
import com.example.appointment.model.entity.MedicalRecord;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.repository.MedicalRecordRepo;
import com.example.appointment.repository.PatientRepo;
import com.example.appointment.service.interfaces.MedicalRecordServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MedicalRecordService implements MedicalRecordServiceInterface {
    private final MedicalRecordRepo medicalRecordRepository;
    private final PatientRepo patientRepository;

    @Override
    public MedicalRecordDto create(MedicalRecordCreationRequest medicalRecordReq) {
        validateMedicalRecord(medicalRecordReq);

        Long patientId = medicalRecordReq.patientId();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + patientId));

        MedicalRecord medicalRecord=new MedicalRecord(medicalRecordReq.diagnosis(), medicalRecordReq.note(), patient, medicalRecordReq.issueDate());

        return ToDTo.medicalRecordToDto( medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public MedicalRecordDto update(Long id, MedicalRecordUpdateRequest medicalRecordReq) {

        MedicalRecord existing = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medical Record not found with id: " + id));



        if(medicalRecordReq.diagnosis()!=null)existing.setDiagnosis(medicalRecordReq.diagnosis());
        if(medicalRecordReq.note()!=null)existing.setNote(medicalRecordReq.note());
        existing.setIssueDate(medicalRecordReq.issueDate());

        return ToDTo.medicalRecordToDto(medicalRecordRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordDto getById(Long id) {
        return ToDTo.medicalRecordToDto(medicalRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medical record not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordDto> getAll(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable).map(ToDTo::medicalRecordToDto);
    }

    @Override
    public void delete(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medical Record not found with id: " + id));
        medicalRecordRepository.delete(medicalRecord);
    }

    private void validateMedicalRecord(MedicalRecordCreationRequest medicalRecordReq) {
        if (medicalRecordReq == null) {
            throw new IllegalArgumentException("Medical record must not be null");
        }
        if (medicalRecordReq.patientId() == null ) {
            throw new IllegalArgumentException("Patient must not be null");
        }

    }
}
