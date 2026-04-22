package com.example.appointment.model.entity.human;

import com.example.appointment.model.enums.EmployeeRole;
import com.example.appointment.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Doctor extends Employee {

    @Setter
    @Column(nullable = false)
    private String specialization;

    @Setter
    @Column(nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private LocalTime startWorkingHour;

    @Column(nullable = false)
    private LocalTime endingWorkingHour;

    public Doctor(String fName, String lName, String email, String phoneNumber,
                  Gender gender, String username, String password, String specialization,
                  String licenseNumber, LocalTime startWorkingHour, LocalTime endingWorkingHour) {
        super(fName, lName, email, phoneNumber, gender, username, password, EmployeeRole.DOCTOR);
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        // Validation check here
        setStartWorkingHour(startWorkingHour);
        setEndingWorkingHour(endingWorkingHour);
    }

    public void setStartWorkingHour(LocalTime startWorkingHour) {
        if (startWorkingHour != null && startWorkingHour.getHour() < 5) {
            throw new IllegalArgumentException("Working hour cannot be before 05:00");
        }
        this.startWorkingHour = startWorkingHour;
    }

    public void setEndingWorkingHour(LocalTime endingWorkingHour) {
        if (endingWorkingHour != null && endingWorkingHour.getHour() < 5) {
            throw new IllegalArgumentException("Working hour cannot be before 05:00");
        }
        this.endingWorkingHour = endingWorkingHour;
    }
}
