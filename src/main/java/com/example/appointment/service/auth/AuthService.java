package com.example.appointment.service.auth;

import com.example.appointment.config.JwtService;
import com.example.appointment.error.BadRequestException;
import com.example.appointment.error.NotFoundException;
import com.example.appointment.model.dto.auth.AuthenticationRequest;
import com.example.appointment.model.dto.auth.AuthenticationResponse;
import com.example.appointment.model.dto.requests.doctor.DoctorCreationRequest;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistCreationRequest;
import com.example.appointment.model.entity.human.Doctor;
import com.example.appointment.model.entity.human.Employee;
import com.example.appointment.model.entity.human.Receptionist;
import com.example.appointment.repository.DoctorRepo;
import com.example.appointment.repository.ReceptionistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final DoctorRepo doctorRepo;
    private final ReceptionistRepo receptionistRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerDoctor(DoctorCreationRequest request) {
        ensureUsernameAvailable(request.username());
        ensureEmailAvailable(request.email());
        ensureLicenseNumberAvailable(request.licenseNumber());

        Doctor doctor = new Doctor(
                request.fName(),
                request.lName(),
                request.email(),
                request.phoneNumber(),
                request.gender(),
                request.username(),
                passwordEncoder.encode(request.password()),
                request.specialization(),
                request.licenseNumber(),
                request.startWorkingHour(),
                request.endingWorkingHour()
        );

        Doctor savedDoctor = doctorRepo.save(doctor);
        return buildAuthResponse(savedDoctor);
    }

    public AuthenticationResponse registerReceptionist(ReceptionistCreationRequest request) {
        ensureUsernameAvailable(request.username());
        ensureEmailAvailable(request.email());

        Receptionist receptionist = new Receptionist(
                request.fName(),
                request.lName(),
                request.email(),
                request.phoneNumber(),
                request.gender(),
                request.username(),
                passwordEncoder.encode(request.password()),
                request.deskNumber()
        );

        Receptionist savedReceptionist = receptionistRepo.save(receptionist);
        return buildAuthResponse(savedReceptionist);
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Employee employee = findByUsername(request.username());
        return buildAuthResponse(employee);
    }

    @Transactional(readOnly = true)
    public Employee findByUsername(String username) {
        return doctorRepo.findByUsername(username)
                .map(Employee.class::cast)
                .or(() -> receptionistRepo.findByUsername(username).map(Employee.class::cast))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private AuthenticationResponse buildAuthResponse(Employee employee) {
        String jwtToken = jwtService.generateToken(employee);
        return new AuthenticationResponse(
                employee.getId(),
                employee.getUsername(),
                employee.getRole(),
                jwtToken
        );
    }

    private void ensureUsernameAvailable(String username) {
        if (doctorRepo.existsByUsername(username) || receptionistRepo.existsByUsername(username)) {
            throw new BadRequestException("Username is already taken");
        }
    }

    private void ensureEmailAvailable(String email) {
        if (doctorRepo.existsByEmail(email) || receptionistRepo.existsByEmail(email)) {
            throw new BadRequestException("Email is already in use");
        }
    }

    private void ensureLicenseNumberAvailable(String licenseNumber) {
        if (doctorRepo.existsByLicenseNumber(licenseNumber)) {
            throw new BadRequestException("License number is already in use");
        }
    }
}
