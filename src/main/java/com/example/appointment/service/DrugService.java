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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugService implements DrugServiceInterface {
    private final DrugRepo drugRepository;

    @Override
    public DrugDto create(DrugCreationRequest drugReq) {
        if (drugReq == null) {
            throw new IllegalArgumentException("Drug must not be null");
        }
        if (drugReq.name() == null || drugReq.name().isBlank()) {
            throw new IllegalArgumentException("Drug name must not be blank");
        }
        if (drugRepository.existsByNameIgnoreCase(drugReq.name())) {
            throw new IllegalArgumentException("Drug with this name already exists");
        }

        Drug drug=new Drug(drugReq.name(), drugReq.description(), drugReq.price(), drugReq.category(), drugReq.quantity());
        return ToDTo.drugToDto(drugRepository.save(drug));
    }

    @Override
    public DrugDto update(Long id, DrugUpdateRequest drugReq) {
        if (drugReq == null) {
            throw new IllegalArgumentException("Drug must not be null");
        }

        Drug existing = drugRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drug not found with id: " + id));

        if (drugReq.name() != null
                && !drugReq.name().equalsIgnoreCase(existing.getName())
                && drugRepository.existsByNameIgnoreCase(drugReq.name())) {
            throw new IllegalArgumentException("Drug with this name already exists");
        }

        if(drugReq.name()!=null)existing.setName(drugReq.name());
        if(drugReq.description()!=null)existing.setDescription(drugReq.description());
        if(drugReq.price()!=null)existing.setPrice(drugReq.price());
        if(drugReq.category()!=null)existing.setCategory(drugReq.category());
        if(drugReq.quantity()!=null)existing.setQuantity(drugReq.quantity());


        return ToDTo.drugToDto(drugRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public DrugDto getById(Long id) {
        return ToDTo.drugToDto(drugRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drug not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DrugDto> getAll(DrugFilter filter, Pageable pageable) {

        return drugRepository.findAll(DrugSpecification.withFilter(filter), pageable).map(ToDTo::drugToDto);
    }

    @Override
    public void delete(Long id) {
        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Drug not found with id: " + id));
        drugRepository.delete(drug);
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
