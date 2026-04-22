package com.example.appointment.model.entity.human;

import com.example.appointment.model.enums.Gender;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
public class Patient extends Person{

    private String bloodType;
    private String allergy;
    private LocalDate dateOfBirth;
    public Patient(String fName, String lName,
                   String email, String phoneNumber, Gender gender, String bloodType, LocalDate dateOfBirth, String allergy){
        super(fName, lName, email, phoneNumber, gender);
        setDateOfBirth(dateOfBirth);
        setBloodType(bloodType);
        setAllergy(allergy);

    }
}
