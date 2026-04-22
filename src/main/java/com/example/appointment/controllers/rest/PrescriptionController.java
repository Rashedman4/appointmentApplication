package com.example.appointment.controllers.rest;

import com.example.appointment.model.dto.PrescriptionDto;
import com.example.appointment.model.dto.requests.prescription.DrugIntakeCreationRequest;
import com.example.appointment.model.dto.requests.prescription.PrescriptionCreationRequest;
import com.example.appointment.service.interfaces.PrescriptionServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionServiceInterface prescriptionService;

    @PostMapping
    public PrescriptionDto create(@Valid @RequestBody PrescriptionCreationRequest request) {
        return  prescriptionService.create(request);
    }

    @GetMapping("/{id}")
    public PrescriptionDto getById(@PathVariable Long id) {
        return prescriptionService.getById(id);
    }

    @GetMapping
    public Page<PrescriptionDto> getAll(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return prescriptionService.getAll(pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        prescriptionService.delete(id);
    }

    @PostMapping("/{id}/intakes")
    public PrescriptionDto addIntake(@PathVariable Long id, @Valid @RequestBody DrugIntakeCreationRequest request) {
        return prescriptionService.addDrudIntake(id, request);
    }

    @DeleteMapping("/{id}/intakes/{intakeId}")
    public PrescriptionDto deleteIntake(@PathVariable Long id, @PathVariable Long intakeId) {
        return prescriptionService.deleteDrugIntake(id, intakeId);
    }
}
