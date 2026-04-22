package com.example.appointment.controllers.rest;

import com.example.appointment.filter.PatientFilter;
import com.example.appointment.model.dto.human.PatientDto;
import com.example.appointment.model.dto.requests.patient.PatientCreationRequest;
import com.example.appointment.model.dto.requests.patient.PatientUpdateRequest;
import com.example.appointment.service.interfaces.PatientInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientInterface patientService;

    @PostMapping
    public PatientDto create(@Valid @RequestBody PatientCreationRequest request) {
        return patientService.create(request);
    }

    @PutMapping("/{id}")
    public PatientDto update(@PathVariable Long id, @Valid @RequestBody PatientUpdateRequest request) {
        return patientService.update(id, request);
    }

    @GetMapping("/{id}")
    public PatientDto getById(@PathVariable Long id) {
        return patientService.getById(id);
    }

    @GetMapping
    public Page<PatientDto> getAll(@ParameterObject PatientFilter filter,@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return patientService.getAll(filter, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        patientService.delete(id);
    }
}