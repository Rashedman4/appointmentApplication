package com.example.appointment.controllers.rest;

import com.example.appointment.filter.DoctorFilter;
import com.example.appointment.model.dto.human.DoctorDto;
import com.example.appointment.model.dto.requests.doctor.DoctorCreationRequest;
import com.example.appointment.model.dto.requests.doctor.DoctorUpdateRequest;
import com.example.appointment.service.interfaces.DoctorServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorServiceInterface doctorService;

    @PostMapping
    public DoctorDto create(@Valid @RequestBody DoctorCreationRequest request) {
        return doctorService.create(request);
    }

    @PutMapping("/{id}")
    public DoctorDto update(@PathVariable Long id, @Valid @RequestBody DoctorUpdateRequest request) {
        return doctorService.update(id, request);
    }

    @GetMapping("/{id}")
    public DoctorDto getById(@Valid @PathVariable Long id) {
        return doctorService.getByID(id);
    }

    @GetMapping
    public Page<DoctorDto> getAll(@ParameterObject DoctorFilter filter,@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return doctorService.getAll(filter, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        doctorService.delete(id);
    }
}
