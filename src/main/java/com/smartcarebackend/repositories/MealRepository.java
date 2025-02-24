package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    // 특정 환자(resId)의 식사 일지 조회
    List<Meal> findByResident_ResId(Long resId);
    //식사 테이블 최신날짜로 조회
    Optional<Meal> findTopByOrderByMeaDtDesc();
    //특정 날짜에 이미 식사 기록이 있는지 확인
    //boolean existsByMeaDtAndResident_ResId(String meaDt, Long resId);
}
