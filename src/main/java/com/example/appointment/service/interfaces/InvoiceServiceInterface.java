package com.example.appointment.service.interfaces;

import com.example.appointment.filter.InvoiceFilter;
import com.example.appointment.model.dto.InvoiceDto;
import com.example.appointment.model.dto.requests.invoice.InvoiceCreationRequest;
import com.example.appointment.model.enums.PaymentMethod;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface InvoiceServiceInterface {
    InvoiceDto create(InvoiceCreationRequest Invoice);


    InvoiceDto getById(Long id);

    Page<InvoiceDto> getAll(InvoiceFilter filter, Pageable pageable);

    void delete (Long id);

    InvoiceDto markAsPaid(Long id, PaymentMethod method);

    InvoiceDto markAsCancelled(Long id);

}
