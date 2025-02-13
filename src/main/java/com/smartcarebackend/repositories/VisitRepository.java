package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    //Visit엔티티의 guardId값을 기준으로 모든 예약 조회
    List<Visit> findAllByGuard_GuardId(Long guardId);
}
