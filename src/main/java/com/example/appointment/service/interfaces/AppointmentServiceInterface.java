package com.example.appointment.service.interfaces;

import com.example.appointment.filter.AppointmentFilter;
import com.example.appointment.model.dto.AppointmentDto;
import com.example.appointment.model.dto.requests.appointment.AppointmentCreateRequest;
import com.example.appointment.model.dto.requests.appointment.AppointmentUpdateRequest;
import com.example.appointment.model.dto.requests.appointment.CheckAvailabilityRequest;
import com.example.appointment.model.dto.requests.appointment.DateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentServiceInterface {
    AppointmentDto createAppointment(AppointmentCreateRequest appointment);
    AppointmentDto updateAppointment(Long id, AppointmentUpdateRequest appointment);
    AppointmentDto getByID(Long id);
    Page<AppointmentDto> getAll(AppointmentFilter filter, Pageable pageable);
    void delete (Long id);
    boolean isDoctorAvailable(Long doctorId, CheckAvailabilityRequest checkAvailabilityRequest);
    AppointmentDto approve(Long id);
    AppointmentDto cancel(Long id);
    AppointmentDto complete(Long id);
    List<AppointmentDto> getDoctorAppointmentsByDate(Long doctorId, DateRequest dateRequest);


}
