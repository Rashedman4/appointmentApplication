package com.example.appointment.model.entity.human;

import com.example.appointment.model.entity.BaseEntity;
import com.example.appointment.model.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Getter
@Setter
public abstract class Person extends BaseEntity {


    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;


    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender=Gender.MALE;

    @Column(nullable = false)
    private String phoneNumber;



    public Person(String fName,String lName,
                  String email, String phoneNumber,Gender gender){
        setFName(fName);
        setLName(lName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setGender(gender);
    }




}
