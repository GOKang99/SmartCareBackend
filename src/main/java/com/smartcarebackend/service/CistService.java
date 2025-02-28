package com.smartcarebackend.service;

import com.smartcarebackend.dto.CistDTO;

import java.util.List;

public interface CistService {
    // 생성
    CistDTO createCist(CistDTO cistDTO);
    // 업데이트
    CistDTO updateCist(Long id, CistDTO cistDTO);
    // 삭제
    void deleteCist(Long id);
    // 환자별 cist 조회
    List<CistDTO> getCistByResident(Long residentId);
}
