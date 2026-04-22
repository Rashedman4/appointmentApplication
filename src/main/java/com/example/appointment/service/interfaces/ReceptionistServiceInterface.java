package com.example.appointment.service.interfaces;

import com.example.appointment.model.dto.human.ReceptionistDto;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistCreationRequest;
import com.example.appointment.model.dto.requests.receptionist.ReceptionistUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReceptionistServiceInterface {
    ReceptionistDto create(ReceptionistCreationRequest receptionistCreationRequest);
    ReceptionistDto update(Long id, ReceptionistUpdateRequest receptionistUpdateRequest);
    ReceptionistDto getById(Long id);
    Page<ReceptionistDto> getAll(Pageable pageable);
    void delete (Long id);
}
