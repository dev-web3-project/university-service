package com.lms.university.repository;

import com.lms.university.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    boolean existsByName(String name);
}
