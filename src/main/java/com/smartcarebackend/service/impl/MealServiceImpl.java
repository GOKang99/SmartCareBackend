package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.MealDTO;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Meal;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.MealRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private GiverRepository giverRepository;

    // 환자 식사 일지 조회 (환자 페이지)
    @Override
    public List<MealDTO> getMealForResident(Long resId) {
        List<Meal> meals = mealRepository.findByResident_ResId(resId); // 환자 식사 일지 조회
        List<MealDTO> mealDTOs = new ArrayList<>();

        for (Meal meal : meals) {
            MealDTO mealDTO = new MealDTO();
            mealDTO.setMedId(meal.getMedId());
            mealDTO.setMeaDt(meal.getMeaDt());
            mealDTO.setFundDis(meal.getFundDis());
            mealDTO.setBreTp(meal.getBreTp());
            mealDTO.setBreQty(meal.getBreQty());

            mealDTO.setLunTp(meal.getLunTp());
            mealDTO.setLunQty(meal.getLunQty());

            mealDTO.setDinTp(meal.getDinTp());
            mealDTO.setDinQty(meal.getDinQty());

            mealDTO.setMorSnackQty(meal.getMorSnackQty());

            mealDTO.setAftSnackQty(meal.getAftSnackQty());

            mealDTO.setRemark(meal.getRemark());
            mealDTO.setResMealId(meal.getResident().getResId()); // 환자 ID
            mealDTOs.add(mealDTO);
        }

        return mealDTOs;
    }

    // 관리자 식사 일지 조회 (관리자 페이지)
    @Override
    public List<MealDTO> getAllMealsForAdmin() {
        List<Meal> meals = mealRepository.findAll(); // 모든 식사 일지 조회
        List<MealDTO> mealDTOs = new ArrayList<>();

        for (Meal meal : meals) {
            MealDTO mealDTO = new MealDTO();
            mealDTO.setMedId(meal.getMedId());
            mealDTO.setMeaDt(meal.getMeaDt());
            mealDTO.setFundDis(meal.getFundDis());
            mealDTO.setBreTp(meal.getBreTp());
            mealDTO.setBreQty(meal.getBreQty());
            mealDTO.setResName(meal.getResident().getResName());
            mealDTO.setLunTp(meal.getLunTp());
            mealDTO.setLunQty(meal.getLunQty());
            mealDTO.setDinTp(meal.getDinTp());
            mealDTO.setDinQty(meal.getDinQty());
            mealDTO.setMorSnackQty(meal.getMorSnackQty());
            mealDTO.setAftSnackQty(meal.getAftSnackQty());
            mealDTO.setRemark(meal.getRemark());
            mealDTO.setResMealId(meal.getResident().getResId()); // 환자 ID
            mealDTO.setGiver(meal.getGiver().getGiverId());
            mealDTOs.add(mealDTO);
        }

        return mealDTOs;
    }

    // 관리자 식사 일지 선택 조회 (관리자 페이지)
    @Override
    public List<MealDTO> getMealsForAdmin(Long resId) {
        List<Meal> meals = mealRepository.findByResident_ResId(resId); // 모든 식사 일지 조회
        List<MealDTO> mealDTOs = new ArrayList<>();

        for (Meal meal : meals) {
            MealDTO mealDTO = new MealDTO();
            mealDTO.setMedId(meal.getMedId());
            mealDTO.setMeaDt(meal.getMeaDt());
            mealDTO.setFundDis(meal.getFundDis());
            mealDTO.setBreTp(meal.getBreTp());
            mealDTO.setBreQty(meal.getBreQty());
            mealDTO.setResName(meal.getResident().getResName());
            mealDTO.setLunTp(meal.getLunTp());
            mealDTO.setLunQty(meal.getLunQty());
            mealDTO.setDinTp(meal.getDinTp());
            mealDTO.setDinQty(meal.getDinQty());
            mealDTO.setMorSnackQty(meal.getMorSnackQty());
            mealDTO.setAftSnackQty(meal.getAftSnackQty());
            mealDTO.setRemark(meal.getRemark());
            mealDTO.setResMealId(meal.getResident().getResId()); // 환자 ID
            mealDTO.setGiver(meal.getGiver().getGiverId());
            mealDTOs.add(mealDTO);
        }

        return mealDTOs;
    }
    // 관리자 식사 일지 저장
    @Override
    public MealDTO saveMealForAdmin(MealDTO mealDTO) {
        // 1️⃣ 필수 필드가 비어있는지 체크
        if (mealDTO.getResMealId() == null) {
            throw new IllegalArgumentException("Resident ID가 없습니다.");
        }
        if (mealDTO.getGiver() == null) {
            throw new IllegalArgumentException("Giver ID가 없습니다.");
        }

        // 2️⃣ Resident 객체 조회 (식사 일지를 추가할 대상 환자 찾기)
        Resident resident = residentRepository.findById(mealDTO.getResMealId())
                .orElseThrow(() -> new IllegalArgumentException("해당 Resident를 찾을 수 없습니다: " + mealDTO.getResMealId()));

        // 3️⃣ Giver 객체 조회 (관리자가 입력한 제공자 정보 찾기)
        Giver giver = giverRepository.findById(mealDTO.getGiver())
                .orElseThrow(() -> new IllegalArgumentException("해당 Giver를 찾을 수 없습니다: " + mealDTO.getGiver()));

        // 4️⃣ Meal 엔티티 생성 및 설정
        Meal meal = new Meal();
        meal.setMeaDt(mealDTO.getMeaDt());
        meal.setFundDis(mealDTO.getFundDis());
        meal.setBreTp(mealDTO.getBreTp());
        meal.setBreQty(mealDTO.getBreQty());
        meal.setLunTp(mealDTO.getLunTp());
        meal.setLunQty(mealDTO.getLunQty());
        meal.setDinTp(mealDTO.getDinTp());
        meal.setDinQty(mealDTO.getDinQty());
        meal.setMorSnackQty(mealDTO.getMorSnackQty());
        meal.setAftSnackQty(mealDTO.getAftSnackQty());
        meal.setRemark(mealDTO.getRemark());

        // 5️⃣ Resident, Giver 연결
        meal.setResident(resident);
        meal.setGiver(giver);

        // 6️⃣ 데이터 저장
        meal = mealRepository.save(meal);

        // 7️⃣ 저장된 정보 DTO로 반환
        mealDTO.setMedId(meal.getMedId());
        return mealDTO;
    }
    // 관리자 식사 일지 수정
    @Override
    public MealDTO updateMealForAdmin(Long medId, MealDTO mealDTO) {
        Meal meal = mealRepository.findById(medId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found"));

        meal.setFundDis(mealDTO.getFundDis());
        meal.setBreTp(mealDTO.getBreTp());
        meal.setBreQty(mealDTO.getBreQty());
        meal.setLunTp(mealDTO.getLunTp());
        meal.setLunQty(mealDTO.getLunQty());
        meal.setDinTp(mealDTO.getDinTp());
        meal.setDinQty(mealDTO.getDinQty());
        meal.setMorSnackQty(mealDTO.getMorSnackQty());
        meal.setAftSnackQty(mealDTO.getAftSnackQty());

        meal.setRemark(mealDTO.getRemark());

//        Resident resident = residentRepository.findById(mealDTO.getResMealId())
//                .orElseThrow(() -> new IllegalArgumentException("Resident not found"));
//        meal.setResident(resident);
//
//        Giver giver = giverRepository.findById(mealDTO.getGiver())
//                .orElseThrow(() -> new IllegalArgumentException("Giver not found"));
//        meal.setGiver(giver);

        meal = mealRepository.save(meal);
        mealDTO.setMedId(meal.getMedId());
        return mealDTO;
    }

    // 관리자 식사 일지 삭제
    @Override
    public void deleteMealForAdmin(Long medId) {
        Meal meal = mealRepository.findById(medId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found"));
        mealRepository.delete(meal);
    }

}
