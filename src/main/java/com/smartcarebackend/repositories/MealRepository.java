package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Meal;
import com.smartcarebackend.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    // 특정 환자(resId)의 식사 일지 조회
    List<Meal> findByResident_ResId(Long resId);

    List<Meal> findByResidentOrderByMedIdDesc(Resident resident);
}
