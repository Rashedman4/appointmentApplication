package com.example.appointment.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientFilter {
    private String firstName;
    private String lastName;
    private String bloodType;
    private String email;
    private LocalDate dateOfBirthFrom;
    private LocalDate dateOfBirthTo;
}
