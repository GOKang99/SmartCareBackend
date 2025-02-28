package com.smartcarebackend.controller;

import com.smartcarebackend.dto.MealDTO;
import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.dto.UserDTO;
import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.service.MealService;
import com.smartcarebackend.service.ResidentService;
import com.smartcarebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "*")
public class MealController {
    // !!!! 관리자 경로 맵핑은 이대로 사용해도 됨.
    @Autowired
    MealService mealService;
    @Autowired
    GuardRepository guardRepository;
    @Autowired
    ResidentRepository residentRepository;
    // 생성자 주입
//    public MealController(MealService mealService) {
//        this.mealService = mealService;
//    }

    // 환자 식사 일지 조회
    @GetMapping("/resident/{resId}")
    public ResponseEntity<List<MealDTO>> getMealForResident(@PathVariable Long resId) {
        Guard findGuard = guardRepository.findById(resId).get();//가드엔티티 가져오기
        Long findResidentId = findGuard.getResident().getResId();

        // 환자 식사 일지를 조회하는 서비스 호출
        List<MealDTO> mealDTOs = mealService.getMealForResident(findResidentId);
        System.out.println("반환"+mealDTOs);
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

    // 관리자 식사 일지 조회
    @GetMapping("/admin/select")
    public ResponseEntity<List<MealDTO>> getMealsForAdmin(@RequestParam("resMealId") Long resId) {
        System.out.println("레스아이디" + resId);
        List<MealDTO> mealDTOs;
        if (resId == 0) {
            // 관리자가 모든 식사 일지를 조회하는 서비스 호출
            mealDTOs = mealService.getAllMealsForAdmin();
        } else {
            // 관리자가 모든 식사 일지를 조회하는 서비스 호출
            mealDTOs = mealService.getMealsForAdmin(resId);
        }
        return ResponseEntity.ok(mealDTOs); // 조회된 데이터를 200 OK와 함께 반환
    }

    @GetMapping("/admin/residents")
    public ResponseEntity<List<Resident>> getAllMResidentsForAdmin() {
//        String username=principal.getName();
//        User user = userService.findByUsername(username);
//        // 관리자가 모든 식사 일지를 조회하는 서비스 호출
//        List<ResidentDTO> residentDTOS = residentService.getResidentById(user.get());
//        //System.out.println(mealDTOs);
//        return ResponseEntity.ok(mealDTOs); // 조회된 데이터를 200 OK와 함께 반환
        List<Resident> residents = residentRepository.findAll();
        return ResponseEntity.ok(residents);
    }

    // 관리자 식사 일지 저장
    @PostMapping("/admin")
    public ResponseEntity<MealDTO> saveMealForAdmin(@RequestBody MealDTO mealDTO) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("✅ 입력받은 mealDTO: " + mealDTO);

        // 필수 필드가 비어있는지 체크(DTO를 받았느지 확인)
        if (mealDTO.getResMealId() == null) {
            throw new IllegalArgumentException("Resident ID가 없습니다.");
        }
        if (mealDTO.getGiver() == null) {
            throw new IllegalArgumentException("Giver ID가 없습니다.");
        }

        MealDTO savedMeal = mealService.saveMealForAdmin(mealDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeal);
    }


    // 식사 일지 수정 (관리자만)
    @PutMapping("/admin/{medId}")
    public ResponseEntity<MealDTO> updateMealForAdmin(@PathVariable Long medId, @RequestBody MealDTO mealDTO) {
        System.out.println("========================================");
        //System.out.println(medId);
        //System.out.println(mealDTO);
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