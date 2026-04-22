package com.example.appointment.model.dto.requests.invoice;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceCreationRequest(
        @Positive BigDecimal doctorFee, @DecimalMin("0.0") @DecimalMax("100.0") double discount,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate issueDate,
        @NotNull Long doctorId, @NotNull Long patientId, @NotNull Long appointmentId
) {}
