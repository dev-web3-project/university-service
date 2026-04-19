package com.lms.university.service;

import com.lms.university.dto.CycleUpdateDto;
import com.lms.university.entity.Class;
import com.lms.university.entity.Cycle;

import java.util.List;

public interface CycleService {
    List<Cycle> listCycles();
    Cycle getCycleById(Long id);
    Cycle createCycle(Cycle cycle);
    Cycle updateCycle(Long id, CycleUpdateDto cycleUpdateDto);
    String deleteCycle(Long id);
    List<Cycle> getCyclesByEstablishmentId(Long establishmentId);
    List<Cycle> listCyclesWithoutEstablishment();
    String assignClassToCycle(Long cycleId, Long classId);
    String unassignClassFromCycle(Long classId);
}
