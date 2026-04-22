package com.example.appointment.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class DrugIntake extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id",nullable = false)
    private Drug drug;
    @Column(nullable = false)
    private Integer quantity=1;
    @Column(nullable = false)
    private String dosage;
    @Column(nullable = false)
    private String instruction;
    public DrugIntake(Drug drug,String dosage, String instruction){
        setDrug(drug);
        setDosage(dosage);
        setInstruction(instruction);
    }
    public DrugIntake(Drug drug,String dosage, String instruction,Integer quantity){
        setDrug(drug);
        setDosage(dosage);
        setInstruction(instruction);
        setQuantity(quantity);
    }

}
