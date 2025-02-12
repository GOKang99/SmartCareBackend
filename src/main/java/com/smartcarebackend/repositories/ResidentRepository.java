package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    Optional<Resident> findByResName(String resName);

    // resName 유저가 있는지 확인
    Boolean existsByResName(String resName);
}
