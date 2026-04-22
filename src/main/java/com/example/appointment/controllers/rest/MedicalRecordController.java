package com.example.appointment.controllers.rest;

import com.example.appointment.model.dto.MedicalRecordDto;
import com.example.appointment.model.dto.requests.medicalRecord.MedicalRecordCreationRequest;
import com.example.appointment.model.dto.requests.medicalRecord.MedicalRecordUpdateRequest;
import com.example.appointment.service.interfaces.MedicalRecordServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordServiceInterface medicalRecordService;

    @PostMapping
    public MedicalRecordDto create(@Valid @RequestBody MedicalRecordCreationRequest request) {
        return medicalRecordService.create(request);
    }

    @PutMapping("/{id}")
    public MedicalRecordDto update(@PathVariable Long id, @Valid @RequestBody MedicalRecordUpdateRequest request) {
        return medicalRecordService.update(id, request);
    }

    @GetMapping("/{id}")
    public MedicalRecordDto getById(@PathVariable Long id) {
        return medicalRecordService.getById(id);
    }

    @GetMapping
    public Page<MedicalRecordDto> getAll(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return medicalRecordService.getAll(pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        medicalRecordService.delete(id);
    }
}
