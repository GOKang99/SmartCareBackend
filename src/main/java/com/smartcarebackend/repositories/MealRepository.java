package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    // 특정 거주자의 식사 기록 조회
    List<Meal> findByResidentId(Long residentId);
}
