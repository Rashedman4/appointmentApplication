package com.example.appointment.service;

import com.example.appointment.error.NotFoundException;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.PrescriptionDto;
import com.example.appointment.model.dto.requests.prescription.DrugIntakeCreationRequest;
import com.example.appointment.model.dto.requests.prescription.PrescriptionCreationRequest;
import com.example.appointment.model.entity.Appointment;
import com.example.appointment.model.entity.Drug;
import com.example.appointment.model.entity.DrugIntake;
import com.example.appointment.model.entity.Prescription;
import com.example.appointment.repository.AppointmentRepo;
import com.example.appointment.repository.DrugIntakeRepo;
import com.example.appointment.repository.DrugRepo;
import com.example.appointment.repository.PrescriptionRepo;
import com.example.appointment.service.interfaces.PrescriptionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionService implements PrescriptionServiceInterface {
    private final PrescriptionRepo prescriptionRepository;
    private final AppointmentRepo appointmentRepository;
    private final DrugRepo drugRepository;
    private final DrugIntakeRepo drugIntakeRepository;

    @Override
    public PrescriptionDto create(PrescriptionCreationRequest prescriptionReq) {
        Appointment appointment=appointmentRepository.findById(prescriptionReq.appointmentId()).orElseThrow(()-> new NotFoundException("appointment Not found with id "+prescriptionReq.appointmentId()));
        Prescription prescription=new Prescription(appointment);
        Prescription savedPrescription =prescriptionRepository.save(prescription);
        appointment.setPrescription(prescription);
        appointmentRepository.save(appointment);

        return ToDTo.prescriptionToDto(savedPrescription);
    }



    @Override
    @Transactional(readOnly = true)
    public PrescriptionDto getById(Long id) {
        return ToDTo.prescriptionToDto(prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prescription not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDto> getAll(Pageable pageable) {
        return prescriptionRepository.findAll(pageable).map(ToDTo::prescriptionToDto);
    }

    @Override
    public void delete(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prescription not found with id: " + id));
        prescriptionRepository.delete(prescription);
    }

    @Override
    public PrescriptionDto addDrudIntake(Long prescriptionId, DrugIntakeCreationRequest drugIntakeReq) {
          Prescription prescription=  prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new NotFoundException("Prescription not found with id: " + prescriptionId));
        Drug drug=drugRepository.findById(drugIntakeReq.drugId()).orElseThrow(()->new NotFoundException("Drug Not Found with id "+drugIntakeReq.drugId()));
        DrugIntake drugIntake= new DrugIntake(drug, drugIntakeReq.dosage(), drugIntakeReq.instruction(), drugIntakeReq.quantity());
        prescription.addDrugIntake(drugIntake);
        drugIntakeRepository.save(drugIntake);
        return ToDTo.prescriptionToDto(prescriptionRepository.save(prescription));
    }

    @Override
    public PrescriptionDto deleteDrugIntake(Long prescriptionId,Long drugIntakeId) {
        Prescription prescription=prescriptionRepository.findById(prescriptionId).orElseThrow(()->new NotFoundException("Prescription Not Found with Id "+prescriptionId));
        DrugIntake drugIntake=drugIntakeRepository.findById(drugIntakeId).orElseThrow(()->new NotFoundException("Drug intake Not Found with id "+drugIntakeId));
        prescription.removeDrugIntake(drugIntake);
        drugIntakeRepository.deleteById(drugIntakeId);
        return ToDTo.prescriptionToDto(prescriptionRepository.save(prescription));

    }
}
