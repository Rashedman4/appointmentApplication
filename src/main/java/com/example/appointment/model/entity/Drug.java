package com.example.appointment.model.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Drug extends BaseEntity{

    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private int quantity;
    public Drug(String name,String description,BigDecimal price, String category, int quantity){
        setName(name);
        setPrice(price);
        setCategory(category);
        setDescription(description);
        setQuantity(quantity);
    }

}
