package com.example.appointment.mappers;

import com.example.appointment.model.dto.*;
import com.example.appointment.model.dto.human.*;
import com.example.appointment.model.entity.*;
import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Patient;
import com.example.appointment.model.entity.human.Receptionist;

import java.util.List;

public class ToDTo {
    public static AppointmentDto appointmentToDto(Appointment appointment){
        return new AppointmentDto(appointment.getId(), appointment.getAppointmentDate(), appointment.getAppointmentStart(),
                appointment.getAppointmentEnd(),appointment.getStatus(), appointment.getNote(), appointment.getPatient().getId(),
                appointment.getDoctor().getId(),appointment.getCreatedBy().getId());
    }
    public static DoctorDto doctorToDto(Doctor doctor){
        HumanDto human=new HumanDto(doctor.getId(), doctor.getFName(), doctor.getLName(), doctor.getPhoneNumber(),doctor.getGender());
        return new DoctorDto(human, doctor.getSpecialization(),doctor.getStartWorkingHour(),doctor.getEndingWorkingHour());
    }
    public static DoctorRegisterDto doctorRegisterToDto(Doctor doctor,String token){
        HumanDto human=new HumanDto(doctor.getId(), doctor.getFName(), doctor.getLName(), doctor.getPhoneNumber(),doctor.getGender());
        return new DoctorRegisterDto(human, doctor.getSpecialization(),doctor.getStartWorkingHour(),doctor.getEndingWorkingHour(), token);
    }
    public static PatientDto PatientToDto(Patient patient){
        HumanDto human=new HumanDto(patient.getId(), patient.getFName(), patient.getLName(), patient.getPhoneNumber(),patient.getGender());
        return new PatientDto(human, patient.getBloodType(), patient.getDateOfBirth(), patient.getAllergy());

    }

    public static ReceptionistDto receptionistToDto(Receptionist receptionist){
        HumanDto human=new HumanDto(receptionist.getId(), receptionist.getFName(), receptionist.getLName(), receptionist.getPhoneNumber(), receptionist.getGender());
        return new ReceptionistDto(human, receptionist.getDeskNumber());
    }

    public static DrugDto drugToDto(Drug drug){
        return new DrugDto(drug.getId(), drug.getName(), drug.getDescription(), drug.getPrice(), drug.getCategory());
    }
    public static MedicalRecordDto medicalRecordToDto(MedicalRecord medicalRecord){
        return new MedicalRecordDto(medicalRecord.getId(), medicalRecord.getPatient().getId(), medicalRecord.getDiagnosis(), medicalRecord.getNote(), medicalRecord.getIssueDate());
    }
    public static PrescriptionDto prescriptionToDto(Prescription prescription){
        List<DrugIntakeDto> drugsIntake=prescription.getDrugs().stream().map(ToDTo::drugIntakeToDto).toList();
        return new PrescriptionDto(prescription.getId(), drugsIntake, prescription.getId());
    }
    public static DrugIntakeDto drugIntakeToDto(DrugIntake drugIntake){
        return new DrugIntakeDto(drugIntake.getId(), drugToDto(drugIntake.getDrug()), drugIntake.getDosage(), drugIntake.getInstruction(), drugIntake.getQuantity());
    }
    public static InvoiceDto invoiceToDto(Invoice invoice){
        return new InvoiceDto(invoice.getId(), invoice.getTotalAmount(),invoice.getDoctorFee(), invoice.getDiscount(),
        invoice.getPaymentStatus(),  invoice.getPaymentMethod(),invoice.getPatient().getId(),invoice.getDoctor().getId());
    }
}
