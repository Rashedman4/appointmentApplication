package com.example.appointment.service;

import com.example.appointment.error.BadRequestException;
import com.example.appointment.error.NotFoundException;
import com.example.appointment.filter.AppointmentFilter;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.AppointmentDto;
import com.example.appointment.model.dto.requests.appointment.AppointmentCreateRequest;
import com.example.appointment.model.dto.requests.appointment.AppointmentUpdateRequest;
import com.example.appointment.model.dto.requests.appointment.CheckAvailabilityRequest;
import com.example.appointment.model.dto.requests.appointment.DateRequest;
import com.example.appointment.model.entity.Appointment;
import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.model.entity.human.Receptionist;
import com.example.appointment.model.enums.AppointmentStatus;
import com.example.appointment.repository.AppointmentRepo;
import com.example.appointment.repository.DoctorRepo;
import com.example.appointment.repository.PatientRepo;
import com.example.appointment.repository.ReceptionistRepo;
import com.example.appointment.service.interfaces.AppointmentServiceInterface;
import com.example.appointment.specification.AppointmentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AppointmentService implements AppointmentServiceInterface {
    private final AppointmentRepo appointmentRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final ReceptionistRepo receptionistRepo;


    public AppointmentDto createAppointment(AppointmentCreateRequest appointmentReq){
        log.info("Creating appointment: Patient ID {}, Doctor ID {}, Date {}",
                appointmentReq.patientId(), appointmentReq.doctorId(), appointmentReq.appointmentDate());
        if(appointmentReq.appointmentDate().isBefore(LocalDate.now())){
            log.warn("Validation failed: Appointment date {} is in the past", appointmentReq.appointmentDate());
            throw new BadRequestException("The Appointment Date is before the current date");
        }
        if(appointmentReq.appointmentStart().isAfter(appointmentReq.appointmentEnd())){
            log.warn("Validation failed: Start time {} is after end time {}",
                    appointmentReq.appointmentStart(), appointmentReq.appointmentEnd());
            throw new BadRequestException("The Appointment starting time is after the ending time");
        }
        Patient patient = patientRepo.findById(appointmentReq.patientId())
                .orElseThrow(() -> {
                    log.error("Create failed: Patient {} not found", appointmentReq.patientId());
                    return new NotFoundException("Patient Not Found.");
                });

        Doctor doctor = doctorRepo.findById(appointmentReq.doctorId())
                .orElseThrow(() -> {
                    log.error("Create failed: Doctor {} not found", appointmentReq.doctorId());
                    return new NotFoundException("Doctor Not Found.");
                });
        validateDoctorWorkingHours(doctor,appointmentReq.appointmentStart(),appointmentReq.appointmentEnd());
        boolean available = isDoctorAvailable(
                doctor.getId(),
                new CheckAvailabilityRequest(appointmentReq.appointmentDate(),
                appointmentReq.appointmentStart(),
                appointmentReq.appointmentEnd())
        );

        if (!available) {
            throw new IllegalArgumentException("Doctor is not available at the selected date and time");
        }

        Receptionist receptionist=receptionistRepo.findById(appointmentReq.createdById()).orElseThrow(()->new NotFoundException("Receptionist Not Fount."));
        Appointment appointment=new Appointment(appointmentReq.appointmentDate(),appointmentReq.appointmentStart(),
                appointmentReq.appointmentEnd(),appointmentReq.status(),appointmentReq.note(),patient,doctor,receptionist,null);
        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentStatus.PENDING);
        }

        Appointment appointmentSaved=appointmentRepo.save(appointment);
        log.info("Successfully created appointment with ID: {}", appointmentSaved.getId());
        return ToDTo.appointmentToDto(appointmentSaved);
    }

    public AppointmentDto updateAppointment(Long id, AppointmentUpdateRequest appointmentReq){
        log.info("Updating appointment ID: {}", id);
        if(appointmentReq.appointmentDate().isBefore(LocalDate.now())){
            throw new BadRequestException("The Appointment Date is before the current date");
        }
        if(appointmentReq.appointmentStart().isAfter(appointmentReq.appointmentEnd())){
            throw new BadRequestException("The Appointment starting time is after the ending time");
        }

        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() -> {
            log.error("Update failed: Appointment {} not found", id);
            return new NotFoundException("Appointment Not Found.");
        });
        boolean hasConflict = appointmentRepo.existsByDoctorIdAndAppointmentDateAndAppointmentStartLessThanAndAppointmentEndGreaterThanAndIdNot(
                        appointment.getDoctor().getId(),
                        appointmentReq.appointmentDate(),
                        appointmentReq.appointmentEnd(),
                        appointmentReq.appointmentStart(),
                        id
                );

        if (hasConflict) {
            log.warn("Update failed for appointment {}: Time conflict for Doctor {}", id, appointment.getDoctor().getId());
            throw new IllegalArgumentException("Doctor is not available at the selected date and time");
        }

        validateDoctorWorkingHours(
                appointment.getDoctor(),
                appointmentReq.appointmentStart(),
                appointmentReq.appointmentEnd()
        );

        appointment.setAppointmentDate(appointmentReq.appointmentDate());
        appointment.setAppointmentStart(appointmentReq.appointmentStart());
        appointment.setAppointmentEnd(appointmentReq.appointmentEnd());
        appointmentRepo.save(appointment);
        log.info("Appointment {} updated successfully", id);
        return ToDTo.appointmentToDto(appointment);
    }
    @Transactional(readOnly = true)
    public AppointmentDto getByID(Long id){
        Appointment appointment=appointmentRepo.findById(id).orElseThrow(()-> {
            log.warn("Appointment with Id: {} is not Found.",id);
            return new NotFoundException("Appointment Not Found.");
        });
        return ToDTo.appointmentToDto(appointment);

    }
    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAll(AppointmentFilter filter, Pageable pageable) {
        if (filter == null) {
            filter = new AppointmentFilter();
        }
        return appointmentRepo.findAll(AppointmentSpecification.withFilter(filter), pageable).map(ToDTo::appointmentToDto);


    }

    @Override
    public void delete(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() -> {
            log.error("Delete failed: Appointment {} not found", id);
            return new NotFoundException("Appointment Not Found.");
        });
        appointmentRepo.delete(appointment);
        log.info("Appointment {} deleted successfully", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isDoctorAvailable(Long doctorId, CheckAvailabilityRequest checkAvailabilityRequest) {
        if (doctorId == null) {
            throw new IllegalArgumentException("Doctor id must not be null");
        }
        if (checkAvailabilityRequest.date() == null) {
            throw new IllegalArgumentException("Appointment date must not be null");
        }
        if (checkAvailabilityRequest.start() == null || checkAvailabilityRequest.end() == null) {
            throw new IllegalArgumentException("Appointment start and end time must not be null");
        }
        if (!checkAvailabilityRequest.start().isBefore(checkAvailabilityRequest.end())) {
            throw new IllegalArgumentException("Appointment start time must be before end time");
        }

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + doctorId));

        validateDoctorWorkingHours(doctor, checkAvailabilityRequest.start(), checkAvailabilityRequest.end());

        return !appointmentRepo
                .existsByDoctorIdAndAppointmentDateAndAppointmentStartLessThanAndAppointmentEndGreaterThan(
                        doctorId,
                        checkAvailabilityRequest.date(),
                        checkAvailabilityRequest.end(),
                        checkAvailabilityRequest.start()
                );
    }

    @Override
    public AppointmentDto approve(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(()->new NotFoundException("Appointment Not Found."));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled appointment cannot be approved");
        }

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Completed appointment cannot be approved");
        }

        appointment.setStatus(AppointmentStatus.APPROVED);
        return ToDTo.appointmentToDto(appointmentRepo.save(appointment));
    }

    @Override
    public AppointmentDto cancel(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(()->new NotFoundException("Appointment Not Found."));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Completed appointment cannot be cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return ToDTo.appointmentToDto(appointmentRepo.save(appointment));
    }

    @Override
    public AppointmentDto complete(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(()->new NotFoundException("Appointment Not Found."));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled appointment cannot be completed");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        return ToDTo.appointmentToDto(appointmentRepo.save(appointment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> getDoctorAppointmentsByDate(Long doctorId, DateRequest dateRequest) {
        if (doctorId == null) {
            throw new IllegalArgumentException("Doctor id must not be null");
        }
        if (dateRequest.date() == null) {
            throw new IllegalArgumentException("Date must not be null");
        }

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + doctorId));

        return appointmentRepo.findByDoctorAndAppointmentDate(doctor, dateRequest.date()).stream().map(ToDTo::appointmentToDto).toList();
    }


    private void validateDoctorWorkingHours(Doctor doctor, LocalTime start, LocalTime end) {
        if (doctor.getStartWorkingHour() == null || doctor.getEndingWorkingHour() == null) {
            throw new IllegalStateException("Doctor working hours are not configured");
        }

        boolean startsWithinHours = !start.isBefore(doctor.getStartWorkingHour());
        boolean endsWithinHours = !end.isAfter(doctor.getEndingWorkingHour());

        if (!startsWithinHours || !endsWithinHours) {
            throw new IllegalArgumentException("Appointment must be within doctor's working hours");
        }
    }
}
