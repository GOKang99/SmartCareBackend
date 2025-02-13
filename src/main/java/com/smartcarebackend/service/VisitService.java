package com.smartcarebackend.service;

import com.smartcarebackend.dto.VisitDTO;
import com.smartcarebackend.model.Visit;

import java.util.List;

public interface VisitService {
    //모든 예약 조회 (관리자용)
    List<VisitDTO> getAllVisits();
    //Giver보호자아이디로 예약 조회
    List<VisitDTO> getAllVisitsByGuardId(Long guardId);
}
