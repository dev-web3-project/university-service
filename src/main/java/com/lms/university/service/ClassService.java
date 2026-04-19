package com.lms.university.service;

import com.lms.university.dto.ClassUpdateDto;
import com.lms.university.entity.Class;
import com.lms.university.entity.Genie;

import java.util.List;

public interface ClassService {
    List<Class> listClasses();
    Class getClassById(Long id);
    Class createClass(Class classRef);
    Class updateClass(Long id, ClassUpdateDto classUpdateDto);
    String deleteClass(Long id);
    List<Class> getClassesByCycleId(Long cycleId);
    List<Class> listClassesWithoutCycle();
    String assignGenieToClass(Long classId, Long genieId);
    String unassignGenieFromClass(Long genieId);
}
