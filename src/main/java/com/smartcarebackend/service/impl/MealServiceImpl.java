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
            mealDTO.setBreTime(meal.getBreTime());
            mealDTO.setLunTp(meal.getLunTp());
            mealDTO.setLunQty(meal.getLunQty());
            mealDTO.setLunTime(meal.getLunTime());
            mealDTO.setDinTp(meal.getDinTp());
            mealDTO.setDinQty(meal.getDinQty());
            mealDTO.setDinTime(meal.getDinTime());
            mealDTO.setMorSnackQty(meal.getMorSnackQty());
            mealDTO.setMorSnackTime(meal.getMorSnackTime());
            mealDTO.setAftSnackQty(meal.getAftSnackQty());
            mealDTO.setAftSnackTime(meal.getAftSnackTime());
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
            mealDTO.setBreTime(meal.getBreTime());
            mealDTO.setLunTp(meal.getLunTp());
            mealDTO.setLunQty(meal.getLunQty());
            mealDTO.setLunTime(meal.getLunTime());
            mealDTO.setDinTp(meal.getDinTp());
            mealDTO.setDinQty(meal.getDinQty());
            mealDTO.setDinTime(meal.getDinTime());
            mealDTO.setMorSnackQty(meal.getMorSnackQty());
            mealDTO.setMorSnackTime(meal.getMorSnackTime());
            mealDTO.setAftSnackQty(meal.getAftSnackQty());
            mealDTO.setAftSnackTime(meal.getAftSnackTime());
            mealDTO.setRemark(meal.getRemark());
            mealDTO.setResMealId(meal.getResident().getResId()); // 환자 ID
            mealDTOs.add(mealDTO);
        }

        return mealDTOs;
    }

    // 관리자 식사 일지 저장
    @Override
    public MealDTO saveMealForAdmin(MealDTO mealDTO) {
        Meal meal = new Meal();
        meal.setMeaDt(mealDTO.getMeaDt());
        meal.setFundDis(mealDTO.getFundDis());
        meal.setBreTp(mealDTO.getBreTp());
        meal.setBreQty(mealDTO.getBreQty());
        meal.setBreTime(mealDTO.getBreTime());
        meal.setLunTp(mealDTO.getLunTp());
        meal.setLunQty(mealDTO.getLunQty());
        meal.setLunTime(mealDTO.getLunTime());
        meal.setDinTp(mealDTO.getDinTp());
        meal.setDinQty(mealDTO.getDinQty());
        meal.setDinTime(mealDTO.getDinTime());
        meal.setMorSnackQty(mealDTO.getMorSnackQty());
        meal.setMorSnackTime(mealDTO.getMorSnackTime());
        meal.setAftSnackQty(mealDTO.getAftSnackQty());
        meal.setAftSnackTime(mealDTO.getAftSnackTime());
        meal.setRemark(mealDTO.getRemark());

        // Resident와 Giver 설정
        Resident resident = residentRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found"));
        meal.setResident(resident);

        Giver giver = giverRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Giver not found"));
        meal.setGiver(giver);

        meal = mealRepository.save(meal);
        mealDTO.setMedId(meal.getMedId()); // 저장된 식사 일지의 ID를 MealDTO에 설정
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
        meal.setBreTime(mealDTO.getBreTime());
        meal.setLunTp(mealDTO.getLunTp());
        meal.setLunQty(mealDTO.getLunQty());
        meal.setLunTime(mealDTO.getLunTime());
        meal.setDinTp(mealDTO.getDinTp());
        meal.setDinQty(mealDTO.getDinQty());
        meal.setDinTime(mealDTO.getDinTime());
        meal.setMorSnackQty(mealDTO.getMorSnackQty());
        meal.setMorSnackTime(mealDTO.getMorSnackTime());
        meal.setAftSnackQty(mealDTO.getAftSnackQty());
        meal.setAftSnackTime(mealDTO.getAftSnackTime());
        meal.setRemark(mealDTO.getRemark());

        Resident resident = residentRepository.findById(mealDTO.getResMealId())
                .orElseThrow(() -> new IllegalArgumentException("Resident not found"));
        meal.setResident(resident);

        Giver giver = giverRepository.findById(mealDTO.getGiver())
                .orElseThrow(() -> new IllegalArgumentException("Giver not found"));
        meal.setGiver(giver);

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
