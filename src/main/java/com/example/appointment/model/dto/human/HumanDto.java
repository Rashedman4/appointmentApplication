package com.example.appointment.model.dto.human;

import com.example.appointment.model.enums.Gender;

public record HumanDto (Long id, String fName, String lName, String phoneNumber, Gender gender){
}
