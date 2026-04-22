package com.example.appointment.filter;

import com.example.appointment.model.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentFilter {
    private Long patientId;
    private Long doctorId;
    private AppointmentStatus status;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalTime timeStart;
    private LocalTime timeEnd;
}
