package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Cist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CistRepository extends JpaRepository<Cist, Long> {

    // 특정 대상자의 Cist 기록을 날짜순으로 조회
    List<Cist> findByResidentResIdOrderByCisDtDesc(Long resId);
}
