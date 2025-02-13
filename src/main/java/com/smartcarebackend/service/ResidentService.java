package com.smartcarebackend.service;

import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.model.Resident;

import java.util.List;

public interface ResidentService {

    // 입소자 등록
    Resident createResident(ResidentDTO residentDTO);

    // 입소자 수정
    Resident updateResident(Long redId, ResidentDTO residentDTO);

    // 입소자 삭제
    void deleteResident(Long redId);

    // 모든 입소자 조회
    List<Resident> getAllResidents();
}
