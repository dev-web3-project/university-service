package com.lms.university.service.impl;

import com.lms.university.dto.GenieUpdateDto;
import com.lms.university.entity.Genie;
import com.lms.university.exception.ConflictException;
import com.lms.university.exception.NotFoundException;
import com.lms.university.repository.GenieRepository;
import com.lms.university.service.GenieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenieServiceImpl implements GenieService {

    private final GenieRepository genieRepository;

    @Override
    public List<Genie> listGenies() {
        List<Genie> genies = genieRepository.findAll();
        if (genies.isEmpty()) {
            throw new NotFoundException("No genies found");
        }
        return genies;
    }

    @Override
    public Genie getGenieById(Long id) {
        Genie genie = genieRepository.findById(id).orElse(null);
        if (genie == null) {
            throw new NotFoundException("Genie not found");
        }
        return genie;
    }

    @Override
    public Genie createGenie(Genie genie) {
        if (genieRepository.existsByName(genie.getName())) {
            throw new ConflictException("Genie already exists");
        }
        return genieRepository.save(genie);
    }

    @Override
    public Genie updateGenie(Long id, GenieUpdateDto genieUpdateDto) {
        Genie genie = genieRepository.findById(id).orElse(null);
        if (genie == null) {
            throw new NotFoundException("Genie not found");
        }
        genie.setName(genieUpdateDto.getName());
        genie.setDescription(genieUpdateDto.getDescription());
        genie.setPhone(genieUpdateDto.getPhone());
        genie.setEmail(genieUpdateDto.getEmail());
        return genieRepository.save(genie);
    }

    @Override
    public String deleteGenie(Long id) {
        Genie genie = genieRepository.findById(id).orElse(null);
        if (genie == null) {
            throw new NotFoundException("Genie not found");
        }
        genieRepository.delete(genie);
        return "Genie deleted successfully";
    }

    @Override
    public List<Genie> getGeniesByClassId(Long classId) {
        List<Genie> genies = genieRepository.findByClassRefId(classId);
        if (genies.isEmpty()) {
            throw new NotFoundException("No genies found");
        }
        return genies;
    }

    @Override
    public List<Genie> listGeniesWithoutClass() {
        List<Genie> genies = genieRepository.findByClassRefIsNull();
        if (genies.isEmpty()) {
            throw new NotFoundException("No genies found");
        }
        return genies;
    }
}
