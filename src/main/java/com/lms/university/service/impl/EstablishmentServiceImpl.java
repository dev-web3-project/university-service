package com.lms.university.service.impl;

import com.lms.university.dto.EstablishmentUpdateDto;
import com.lms.university.entity.Cycle;
import com.lms.university.entity.Establishment;
import com.lms.university.exception.ConflictException;
import com.lms.university.exception.NotFoundException;
import com.lms.university.repository.CycleRepository;
import com.lms.university.repository.EstablishmentRepository;
import com.lms.university.service.EstablishmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {

    private final EstablishmentRepository establishmentRepository;
    private final CycleRepository cycleRepository;

    @Override
    public List<Establishment> listEstablishments() {
        List<Establishment> establishments = establishmentRepository.findAll();
        if (establishments.isEmpty()) {
            throw new NotFoundException("No establishments found");
        }
        return establishments;
    }

    @Override
    public Establishment getEstablishmentById(Long id) {
        Establishment establishment = establishmentRepository.findById(id).orElse(null);
        if (establishment == null) {
            throw new NotFoundException("Establishment not found");
        }
        return establishment;
    }

    @Override
    public Establishment createEstablishment(Establishment establishment) {
        if (establishmentRepository.existsByName(establishment.getName())) {
            throw new ConflictException("Establishment already exists");
        }
        return establishmentRepository.save(establishment);
    }

    @Override
    public Establishment updateEstablishment(Long id, EstablishmentUpdateDto establishmentUpdateDto) {
        Establishment establishment = establishmentRepository.findById(id).orElse(null);
        if (establishment == null) {
            throw new NotFoundException("Establishment not found");
        }
        establishment.setName(establishmentUpdateDto.getName());
        establishment.setDescription(establishmentUpdateDto.getDescription());
        establishment.setPhone(establishmentUpdateDto.getPhone());
        establishment.setEmail(establishmentUpdateDto.getEmail());
        establishment.setDeanId(establishmentUpdateDto.getDeanId());
        return establishmentRepository.save(establishment);
    }

    @Override
    public String deleteEstablishment(Long id) {
        Establishment establishment = establishmentRepository.findById(id).orElse(null);
        if (establishment == null) {
            throw new NotFoundException("Establishment not found");
        }
        establishmentRepository.delete(establishment);
        return "Establishment deleted successfully";
    }

    @Override
    public String assignCycleToEstablishment(Long establishmentId, Long cycleId) {
        Establishment establishment = establishmentRepository.findById(establishmentId).orElse(null);
        if (establishment == null) {
            throw new NotFoundException("Establishment not found");
        }
        Cycle cycle = cycleRepository.findById(cycleId).orElse(null);
        if (cycle == null) {
            throw new NotFoundException("Cycle not found");
        }
        cycle.setEstablishment(establishment);
        cycleRepository.save(cycle);
        return "Cycle assigned to establishment successfully";
    }

    @Override
    public String unassignCycleFromEstablishment(Long cycleId) {
        Cycle cycle = cycleRepository.findById(cycleId).orElse(null);
        if (cycle == null) {
            throw new NotFoundException("Cycle not found");
        }
        cycle.setEstablishment(null);
        cycleRepository.save(cycle);
        return "Cycle unassigned from establishment successfully";
    }
}
