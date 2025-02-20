package com.smartcarebackend.service;

import com.smartcarebackend.dto.MealDTO;

import java.util.List;

public interface MealService {

    // 환자 식사 일지 조회
    List<MealDTO> getMealForResident(Long resId);

    // 관리자 식사 일지 조회
    List<MealDTO> getAllMealsForAdmin();

    // 관리자 식사 일지 저장
    MealDTO saveMealForAdmin(MealDTO mealDTO);

    // 식사 일지 수정 (관리자만)
    MealDTO updateMealForAdmin(Long medId, MealDTO mealDTO);

    // 식사 일지 삭제 (관리자만)
    void deleteMealForAdmin(Long medId);

}
