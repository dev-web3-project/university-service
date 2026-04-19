package com.lms.university.service;

import com.lms.university.dto.GenieUpdateDto;
import com.lms.university.entity.Genie;

import java.util.List;

public interface GenieService {
    List<Genie> listGenies();
    Genie getGenieById(Long id);
    Genie createGenie(Genie genie);
    Genie updateGenie(Long id, GenieUpdateDto genieUpdateDto);
    String deleteGenie(Long id);
    List<Genie> getGeniesByClassId(Long classId);
    List<Genie> listGeniesWithoutClass();
}
