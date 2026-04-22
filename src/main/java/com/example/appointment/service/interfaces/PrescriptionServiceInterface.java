package com.example.appointment.service.interfaces;

import com.example.appointment.model.dto.PrescriptionDto;
import com.example.appointment.model.dto.requests.prescription.DrugIntakeCreationRequest;
import com.example.appointment.model.dto.requests.prescription.PrescriptionCreationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionServiceInterface {
    PrescriptionDto create(PrescriptionCreationRequest Prescription);

    PrescriptionDto getById(Long id);

    Page<PrescriptionDto> getAll(Pageable pageable);

    void delete(Long id);

    PrescriptionDto addDrudIntake(Long prescriptionId, DrugIntakeCreationRequest drugIntakeReq);
    PrescriptionDto deleteDrugIntake(Long prescriptionId,Long drugIntakeId);



}
