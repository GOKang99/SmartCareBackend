package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Giver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface GiverRepository extends JpaRepository<Giver, Long> {

    // 관리자(giver)를 ID로 조회
    Optional<Giver> findById(Long giverId);

}
