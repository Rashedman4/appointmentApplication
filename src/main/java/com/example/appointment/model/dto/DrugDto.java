package com.example.appointment.model.dto;

import java.math.BigDecimal;

public record DrugDto (Long drugId, String name, String description, BigDecimal price, String category){
}
