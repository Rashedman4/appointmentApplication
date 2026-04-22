package com.example.appointment.repository;

import com.example.appointment.model.entity.Invoice;
import com.example.appointment.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvoiceRepo extends JpaRepository<Invoice,Long>, JpaSpecificationExecutor<Invoice> {
    List<Invoice> findByPaymentStatus(PaymentStatus paymentStatus);
}
