package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Guard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardRepository extends JpaRepository<Guard, Long> {
}
