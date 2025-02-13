package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Cist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CistRepository extends JpaRepository<Cist, Long> {
}
