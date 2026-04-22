package com.example.appointment.controllers.rest;

import com.example.appointment.model.dto.human.ReceptionistDto;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistCreationRequest;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistUpdateRequest;
import com.example.appointment.service.interfaces.ReceptionistServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receptionists")
@RequiredArgsConstructor
public class ReceptionistController {
    private final ReceptionistServiceInterface receptionistService;

    @PostMapping
    public ReceptionistDto create(@Valid @RequestBody ReceptionistCreationRequest request) {
        return receptionistService.create(request);
    }

    @PutMapping("/{id}")
    public ReceptionistDto update(@PathVariable Long id, @Valid @RequestBody ReceptionistUpdateRequest request) {
        return receptionistService.update(id, request);
    }

    @GetMapping("/{id}")
    public ReceptionistDto getById(@PathVariable Long id) {
        return receptionistService.getById(id);
    }

    @GetMapping
    public Page<ReceptionistDto> getAll(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id")Pageable pageable) {
        return receptionistService.getAll(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        receptionistService.delete(id);
    }
}