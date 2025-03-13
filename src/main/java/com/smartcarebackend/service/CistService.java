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

    // 특정 레지던트의 Cist 조회 (관리자 페이지)
    List<CistDTO> getCistsForAdmin(Long resId);

    // 가드id로 연결된 환자의 cist정보 가져오기
    List<CistDTO> getStatusCistByGuardId(Long guardId);
}
