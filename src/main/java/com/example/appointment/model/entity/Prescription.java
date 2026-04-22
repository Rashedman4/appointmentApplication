package com.example.appointment.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Prescription extends BaseEntity{


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prescription_id") // Creates a FK in the drug_intake table
    private List<DrugIntake> drugs = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    @Setter
    private Appointment appointment;
    public Prescription(Appointment appointment){
        setAppointment(appointment);
    }

    public DrugIntake addDrugIntake(DrugIntake drugIntake){
        drugs.add(drugIntake);
        return drugIntake;
    }
    public void removeDrugIntake(DrugIntake drugIntake){
        drugs.remove(drugIntake);
    }

}
