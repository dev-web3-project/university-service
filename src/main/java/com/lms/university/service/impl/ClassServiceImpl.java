package com.lms.university.service.impl;

import com.lms.university.dto.ClassUpdateDto;
import com.lms.university.entity.Class;
import com.lms.university.entity.Genie;
import com.lms.university.exception.ConflictException;
import com.lms.university.exception.NotFoundException;
import com.lms.university.repository.ClassRepository;
import com.lms.university.repository.GenieRepository;
import com.lms.university.service.ClassService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final GenieRepository genieRepository;

    @Override
    public List<Class> listClasses() {
        List<Class> classes = classRepository.findAll();
        if (classes.isEmpty()) {
            throw new NotFoundException("No classes found");
        }
        return classes;
    }

    @Override
    public Class getClassById(Long id) {
        Class classRef = classRepository.findById(id).orElse(null);
        if (classRef == null) {
            throw new NotFoundException("Class not found");
        }
        return classRef;
    }

    @Override
    public Class createClass(Class classRef) {
        if (classRepository.existsByName(classRef.getName())) {
            throw new ConflictException("Class already exists");
        }
        return classRepository.save(classRef);
    }

    @Override
    public Class updateClass(Long id, ClassUpdateDto classUpdateDto) {
        Class classRef = classRepository.findById(id).orElse(null);
        if (classRef == null) {
            throw new NotFoundException("Class not found");
        }
        classRef.setName(classUpdateDto.getName());
        classRef.setDescription(classUpdateDto.getDescription());
        classRef.setPhone(classUpdateDto.getPhone());
        classRef.setEmail(classUpdateDto.getEmail());
        return classRepository.save(classRef);
    }

    @Override
    public String deleteClass(Long id) {
        Class classRef = classRepository.findById(id).orElse(null);
        if (classRef == null) {
            throw new NotFoundException("Class not found");
        }
        classRepository.delete(classRef);
        return "Class deleted successfully";
    }

    @Override
    public List<Class> getClassesByCycleId(Long cycleId) {
        List<Class> classes = classRepository.findByCycleId(cycleId);
        if (classes.isEmpty()) {
            throw new NotFoundException("No classes found");
        }
        return classes;
    }

    @Override
    public List<Class> listClassesWithoutCycle() {
        List<Class> classes = classRepository.findByCycleIsNull();
        if (classes.isEmpty()) {
            throw new NotFoundException("No classes found");
        }
        return classes;
    }

    @Override
    public String assignGenieToClass(Long classId, Long genieId) {
        Class classRef = classRepository.findById(classId).orElse(null);
        if (classRef == null) {
            throw new NotFoundException("Class not found");
        }
        Genie genie = genieRepository.findById(genieId).orElse(null);
        if (genie == null) {
            throw new NotFoundException("Genie not found");
        }
        genie.setClassRef(classRef);
        genieRepository.save(genie);
        return "Genie assigned to class successfully";
    }

    @Override
    public String unassignGenieFromClass(Long genieId) {
        Genie genie = genieRepository.findById(genieId).orElse(null);
        if (genie == null) {
            throw new NotFoundException("Genie not found");
        }
        genie.setClassRef(null);
        genieRepository.save(genie);
        return "Genie unassigned from class successfully";
    }
}
