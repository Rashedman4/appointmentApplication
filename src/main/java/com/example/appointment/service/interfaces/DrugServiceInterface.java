package com.example.appointment.service.interfaces;

import com.example.appointment.filter.DrugFilter;
import com.example.appointment.model.dto.DrugDto;
import com.example.appointment.model.dto.requests.drug.DrugCreationRequest;
import com.example.appointment.model.dto.requests.drug.DrugUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface DrugServiceInterface {
    DrugDto create(DrugCreationRequest Drug);
    DrugDto update(Long id, DrugUpdateRequest Drug);
    DrugDto getById(Long id);
    Page<DrugDto> getAll(DrugFilter filter, Pageable pageable);
    void delete (Long id);

    DrugDto updatePrice(Long id, BigDecimal newPrice);
    DrugDto updateQuantity(Long id, Integer quantity);
}
