package com.lms.university.service;

import com.lms.university.dto.EstablishmentUpdateDto;
import com.lms.university.entity.Cycle;
import com.lms.university.entity.Establishment;

import java.util.List;

public interface EstablishmentService {
    List<Establishment> listEstablishments();
    Establishment getEstablishmentById(Long id);
    Establishment createEstablishment(Establishment establishment);
    Establishment updateEstablishment(Long id, EstablishmentUpdateDto establishmentUpdateDto);
    String deleteEstablishment(Long id);
    String assignCycleToEstablishment(Long establishmentId, Long cycleId);
    String unassignCycleFromEstablishment(Long cycleId);
}
