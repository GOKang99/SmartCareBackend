package com.smartcarebackend.service;

import com.smartcarebackend.dto.CistDTO;
import com.smartcarebackend.dto.MealDTO;

import java.util.List;

public interface CistService {

    // 관리자 밀 정보 조회
    List<CistDTO> getAllCistsForAdmin();
    // 생성
    CistDTO createCist(CistDTO cistDTO);
    // 업데이트
    CistDTO updateCist(Long id, CistDTO cistDTO);
    // 삭제
    void deleteCist(Long id);
    // 환자별 cist 조회
    List<CistDTO> getCistByResident(Long residentId);
}
