package com.lms.university.repository;

import com.lms.university.entity.Genie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenieRepository extends JpaRepository<Genie, Long> {
    boolean existsByName(String name);
    List<Genie> findByClassRefId(Long classId);
    List<Genie> findByClassRefIsNull();
}
