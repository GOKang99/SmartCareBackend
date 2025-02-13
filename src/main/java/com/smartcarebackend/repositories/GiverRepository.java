package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Giver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiverRepository extends JpaRepository<Giver, Long> {
}
