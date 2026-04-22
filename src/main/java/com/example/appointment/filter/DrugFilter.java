package com.example.appointment.filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DrugFilter {
    private String name;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minQuantity; // Changed from int to Integer
    private Integer maxQuantity; // Changed from int to Integer
}
