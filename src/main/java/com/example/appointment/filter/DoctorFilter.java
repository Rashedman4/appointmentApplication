package com.example.appointment.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorFilter {
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private String specialization;
}
