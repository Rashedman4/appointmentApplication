package com.example.appointment.service;


import com.example.appointment.error.NotFoundException;
import com.example.appointment.filter.DrugFilter;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.DrugDto;
import com.example.appointment.model.dto.requests.drug.DrugCreationRequest;
import com.example.appointment.model.dto.requests.drug.DrugUpdateRequest;
import com.example.appointment.model.entity.Drug;
import com.example.appointment.repository.DrugRepo;
import com.example.appointment.service.interfaces.DrugServiceInterface;
import com.example.appointment.specification.DrugSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DrugService implements DrugServiceInterface {
    private final DrugRepo drugRepository;

    @Override
    public DrugDto create(DrugCreationRequest drugReq) {
        log.info("Received request to create drug: {}", drugReq.name());

        if (drugReq == null) {
            log.error("Create drug failed: drugReq is null");
            throw new IllegalArgumentException("Drug must not be null");
        }
        if (drugReq.name() == null || drugReq.name().isBlank()) {
            log.warn("Validation failed: Drug name is missing");
            throw new IllegalArgumentException("Drug name must not be blank");
        }
        if (drugRepository.existsByNameIgnoreCase(drugReq.name())) {
            log.warn("Validation failed: Drug name '{}' already exists", drugReq.name());
            throw new IllegalArgumentException("Drug with this name already exists");
        }

        Drug drug=new Drug(drugReq.name(), drugReq.description(), drugReq.price(), drugReq.category(), drugReq.quantity());
        log.info("Successfully created drug with ID: {}", drug.getId());
        return ToDTo.drugToDto(drugRepository.save(drug));
    }

    @Override
    public DrugDto update(Long id, DrugUpdateRequest drugReq) {
        log.info("Updating drug ID: {}", id);
        if (drugReq == null) {
            throw new IllegalArgumentException("Drug must not be null");
        }

        Drug existing = drugRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Drug ID {} not found for update", id);
                    return new NotFoundException("Drug not found with id: " + id);
                });

        if (drugReq.name() != null
                && !drugReq.name().equalsIgnoreCase(existing.getName())
                && drugRepository.existsByNameIgnoreCase(drugReq.name())) {
            log.warn("Update failed: New name '{}' is already taken", drugReq.name());
            throw new IllegalArgumentException("Drug with this name already exists");
        }

        if(drugReq.name()!=null)existing.setName(drugReq.name());
        if(drugReq.description()!=null)existing.setDescription(drugReq.description());
        if(drugReq.price()!=null)existing.setPrice(drugReq.price());
        if(drugReq.category()!=null)existing.setCategory(drugReq.category());
        if(drugReq.quantity()!=null)existing.setQuantity(drugReq.quantity());

        log.info("Drug ID {} updated successfully", id);
        return ToDTo.drugToDto(drugRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public DrugDto getById(Long id) {
        return drugRepository.findById(id)
                .map(ToDTo::drugToDto)
                .orElseThrow(() -> {
                    log.warn("Fetch failed: Drug with ID {} does not exist", id);
                    return new NotFoundException("Drug not found with id: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DrugDto> getAll(DrugFilter filter, Pageable pageable) {

        return drugRepository.findAll(DrugSpecification.withFilter(filter), pageable).map(ToDTo::drugToDto);
    }

    @Override
    public void delete(Long id) {
        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Delete failed: Drug ID {} not found", id);
                    return new NotFoundException("Drug not found with id: " + id);
                });
        drugRepository.delete(drug);
        log.info("Drug ID {} deleted", id);
    }

    @Override
    public DrugDto updatePrice(Long id, BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }

        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drug not found with id: " + id));
        drug.setPrice(newPrice);
        return ToDTo.drugToDto(drugRepository.save(drug));
    }

    @Override
    public DrugDto updateQuantity(Long id, Integer quantity) {
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }

        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drug not found with id: " + id));
        drug.setQuantity(quantity);
        return ToDTo.drugToDto(drugRepository.save(drug));
    }
}
