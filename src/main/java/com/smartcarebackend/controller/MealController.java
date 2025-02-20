package com.smartcarebackend.controller;

import com.smartcarebackend.dto.MealDTO;
import com.smartcarebackend.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "*")
public class MealController {
    // !!!! 관리자 경로 맵핑은 이대로 사용해도 됨.
    @Autowired
    MealService mealService;

    // 생성자 주입
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    // 환자 식사 일지 조회
    @GetMapping("/resident/{resId}")
    public ResponseEntity<List<MealDTO>> getMealForResident(@PathVariable Long resId) {
        // 환자 식사 일지를 조회하는 서비스 호출
        List<MealDTO> mealDTOs = mealService.getMealForResident(resId);
        return ResponseEntity.ok(mealDTOs); // 조회된 데이터를 200 OK와 함께 반환
    }

    // 관리자 식사 일지 조회
    @GetMapping("/admin")
    public ResponseEntity<List<MealDTO>> getAllMealsForAdmin() {
        // 관리자가 모든 식사 일지를 조회하는 서비스 호출
        List<MealDTO> mealDTOs = mealService.getAllMealsForAdmin();
        //System.out.println(mealDTOs);
        return ResponseEntity.ok(mealDTOs); // 조회된 데이터를 200 OK와 함께 반환
    }

    // 관리자 식사 일지 저장
    @PostMapping("/admin")
    public ResponseEntity<MealDTO> saveMealForAdmin(@RequestBody MealDTO mealDTO) {
        // 관리자가 새로운 식사 일지를 저장하는 서비스 호출
        System.out.println("입력받은 값은: "+mealDTO);
        MealDTO savedMeal = mealService.saveMealForAdmin(mealDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeal); // 생성된 데이터를 201 Created와 함께 반환
    }

    // 식사 일지 수정 (관리자만)
    @PutMapping("/admin/{medId}")
    public ResponseEntity<MealDTO> updateMealForAdmin(@PathVariable Long medId, @RequestBody MealDTO mealDTO) {
        // 관리자가 특정 식사 일지를 수정하는 서비스 호출
        MealDTO updatedMeal = mealService.updateMealForAdmin(medId, mealDTO);
        return ResponseEntity.ok(updatedMeal); // 수정된 데이터를 200 OK와 함께 반환
    }

    // 식사 일지 삭제 (관리자만)
    @DeleteMapping("/admin/{medId}")
    public ResponseEntity<Void> deleteMealForAdmin(@PathVariable Long medId) {
        // 관리자가 특정 식사 일지를 삭제하는 서비스 호출
        mealService.deleteMealForAdmin(medId);
        return ResponseEntity.noContent().build(); // 삭제 성공 시 204 No Content 반환
    }
}