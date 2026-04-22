package com.example.appointment.controllers.rest;

import com.example.appointment.filter.AppointmentFilter;
import com.example.appointment.model.dto.AppointmentDto;
import com.example.appointment.model.dto.requests.appointment.AppointmentCreateRequest;
import com.example.appointment.model.dto.requests.appointment.AppointmentUpdateRequest;
import com.example.appointment.model.dto.requests.appointment.CheckAvailabilityRequest;
import com.example.appointment.model.dto.requests.appointment.DateRequest;
import com.example.appointment.service.interfaces.AppointmentServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServiceInterface appointmentService;

    @PostMapping
    public AppointmentDto create(@Valid @RequestBody AppointmentCreateRequest request) {
        return appointmentService.createAppointment(request);
    }

    @PutMapping("/{id}")
    public AppointmentDto update(@PathVariable Long id, @Valid @RequestBody AppointmentUpdateRequest request) {
        return appointmentService.updateAppointment(id, request);
    }

    @GetMapping("/{id}")
    public AppointmentDto getById(@PathVariable Long id) {
        return appointmentService.getByID(id);
    }

    @GetMapping
    public Page<AppointmentDto> getAll(
            @ParameterObject AppointmentFilter filter,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return appointmentService.getAll(filter, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        appointmentService.delete(id);
    }

    @GetMapping("/availability/{doctorId}")
    public boolean checkAvailability(
            @PathVariable Long doctorId, @Valid @RequestBody CheckAvailabilityRequest checkAvailabilityRequest){
        return appointmentService.isDoctorAvailable(doctorId, checkAvailabilityRequest);
    }

    @PatchMapping("/{id}/approve")
    public AppointmentDto approve(@PathVariable Long id) {
        return appointmentService.approve(id);
    }

    @PatchMapping("/{id}/cancel")
    public AppointmentDto cancel(@PathVariable Long id) {
        return appointmentService.cancel(id);
    }

    @PatchMapping("/{id}/complete")
    public AppointmentDto complete(@PathVariable Long id) {
        return appointmentService.complete(id);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentDto> getByDoctorAndDate(
            @PathVariable Long doctorId,@Valid @RequestBody DateRequest dateRequest) {
        return appointmentService.getDoctorAppointmentsByDate(doctorId, dateRequest);
    }
}

