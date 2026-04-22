package com.example.appointment.service;

import com.example.appointment.error.NotFoundException;
import com.example.appointment.filter.InvoiceFilter;
import com.example.appointment.mappers.ToDTo;
import com.example.appointment.model.dto.InvoiceDto;
import com.example.appointment.model.dto.requests.invoice.InvoiceCreationRequest;
import com.example.appointment.model.entity.Appointment;
import com.example.appointment.model.entity.Invoice;
import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.model.enums.PaymentMethod;
import com.example.appointment.model.enums.PaymentStatus;
import com.example.appointment.repository.AppointmentRepo;
import com.example.appointment.repository.DoctorRepo;
import com.example.appointment.repository.InvoiceRepo;
import com.example.appointment.repository.PatientRepo;
import com.example.appointment.service.interfaces.InvoiceServiceInterface;
import com.example.appointment.specification.InvoiceSpecification;
import com.example.appointment.utils.Calculations;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceService implements InvoiceServiceInterface {
    private final InvoiceRepo invoiceRepository;
    private final AppointmentRepo appointmentRepository;
    private final PatientRepo patientRepository;
    private final DoctorRepo doctorRepository;

    @Override
    @Transactional
    public InvoiceDto create(InvoiceCreationRequest invoiceReq) {
        Patient patient= patientRepository.findById(invoiceReq.patientId()).orElseThrow(()->new NotFoundException("Patient not found with id "+invoiceReq.patientId()));
        Doctor doctor=doctorRepository.findById(invoiceReq.doctorId()).orElseThrow(()->new NotFoundException("Doctor Not Found with id "+invoiceReq.doctorId()));
        Appointment appointment=appointmentRepository.findById(invoiceReq.appointmentId()).orElseThrow(()->new NotFoundException("Appointment Not Found with id "+invoiceReq.appointmentId()));
        // Handle null prescription safely
        BigDecimal prescriptionAmount = BigDecimal.ZERO;
        if (appointment.getPrescription() != null) {
            prescriptionAmount = Calculations.calculatePrescriptionAmount(appointment.getPrescription());
        }
        BigDecimal overAllAmount=prescriptionAmount.add(invoiceReq.doctorFee());

        Invoice invoice=new Invoice(overAllAmount,invoiceReq.doctorFee(), invoiceReq.discount(), PaymentStatus.PENDING,null, invoiceReq.issueDate(), doctor,patient,appointment);


        return ToDTo.invoiceToDto(invoiceRepository.save(invoice));
    }



    @Override
    @Transactional(readOnly = true)
    public InvoiceDto getById(Long id) {
        return ToDTo.invoiceToDto(invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvoiceDto> getAll(InvoiceFilter filter, Pageable pageable) {
        if (filter == null) {
            filter = new InvoiceFilter();
        }
        return invoiceRepository.findAll(InvoiceSpecification.withFilter(filter), pageable).map(ToDTo::invoiceToDto);
    }

    @Override
    public void delete(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + id));
        invoiceRepository.delete(invoice);
    }

    @Override
    public InvoiceDto markAsPaid(Long id, PaymentMethod method) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + id));
        invoice.setPaymentStatus(PaymentStatus.PAID);
        invoice.setPaymentMethod(method);
        return ToDTo.invoiceToDto(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceDto markAsCancelled(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + id));
        invoice.setPaymentStatus(PaymentStatus.CANCELLED);
        return ToDTo.invoiceToDto(invoiceRepository.save(invoice));
    }




}
