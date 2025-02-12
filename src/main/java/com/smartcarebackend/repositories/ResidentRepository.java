package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
}
