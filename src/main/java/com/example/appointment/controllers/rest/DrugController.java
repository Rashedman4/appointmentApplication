package com.example.appointment.controllers.rest;

import com.example.appointment.filter.DrugFilter;
import com.example.appointment.model.dto.DrugDto;
import com.example.appointment.model.dto.requests.drug.DrugCreationRequest;
import com.example.appointment.model.dto.requests.drug.DrugUpdateRequest;
import com.example.appointment.service.interfaces.DrugServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {
    private final DrugServiceInterface drugService;

    @PostMapping
    public DrugDto create(@Valid @RequestBody DrugCreationRequest request) {
        return drugService.create(request);
    }

    @PutMapping("/{id}")
    public DrugDto update(@PathVariable Long id, @Valid @RequestBody DrugUpdateRequest request) {
        return drugService.update(id, request);
    }

    @GetMapping("/{id}")
    public DrugDto getById(@PathVariable Long id) {
        return drugService.getById(id);
    }

    @GetMapping
    public Page<DrugDto> getAll(@ParameterObject DrugFilter filter,@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return drugService.getAll(filter, pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        drugService.delete(id);
    }

    @PatchMapping("/{id}/price")
    public DrugDto updatePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
        return drugService.updatePrice(id, newPrice);
    }

    @PatchMapping("/{id}/quantity")
    public DrugDto updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        return drugService.updateQuantity(id, quantity);
    }
}