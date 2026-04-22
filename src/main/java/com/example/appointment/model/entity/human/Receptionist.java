package com.example.appointment.model.entity.human;

import com.example.appointment.model.enums.EmployeeRole;
import com.example.appointment.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receptionist extends Employee{
    @Column(nullable = false)
    private String deskNumber;

        public Receptionist(String fName, String lName,
                            String email, String phoneNumber, Gender gender,
                            String username, String password, String deskNumber){
            super(fName,lName,email,phoneNumber,gender,username,password,EmployeeRole.RECEPTIONIST);
            setDeskNumber(deskNumber);
        }

}
