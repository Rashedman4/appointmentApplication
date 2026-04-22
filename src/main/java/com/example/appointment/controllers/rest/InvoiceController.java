package com.example.appointment.controllers.rest;

import com.example.appointment.filter.InvoiceFilter;
import com.example.appointment.model.dto.InvoiceDto;
import com.example.appointment.model.dto.requests.invoice.InvoiceCreationRequest;
import com.example.appointment.model.enums.PaymentMethod;
import com.example.appointment.service.interfaces.InvoiceServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceServiceInterface invoiceService;

    @PostMapping
    public InvoiceDto create(@Valid @RequestBody InvoiceCreationRequest request) {
        return invoiceService.create(request);
    }

    @GetMapping("/{id}")
    public InvoiceDto getById(@PathVariable Long id) {
        return invoiceService.getById(id);
    }

    @GetMapping
    public Page<InvoiceDto> getAll(@ParameterObject InvoiceFilter filter,@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return invoiceService.getAll(filter, pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        invoiceService.delete(id);
    }

    @PatchMapping("/{id}/pay")
    public InvoiceDto markAsPaid(@PathVariable Long id, @RequestParam PaymentMethod method) {
        return invoiceService.markAsPaid(id, method);
    }

    @PatchMapping("/{id}/cancel")
    public InvoiceDto cancel(@PathVariable Long id) {
        return invoiceService.markAsCancelled(id);
    }
}
