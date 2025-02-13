package com.smartcarebackend.service;

import com.smartcarebackend.dto.VisitDTO;
import com.smartcarebackend.model.Visit;

import java.util.List;
import java.util.Optional;

public interface VisitService {
    //모든 예약 조회 (관리자용)
    List<VisitDTO> getAllVisits();
    //Guard아이디,보호자아이디로 예약 조회
    List<VisitDTO> getAllVisitsByGuardId(Long guardId);

    //보호자+예약번호로 조회
    VisitDTO getVisitByIdAndGuardId(Long visitId, Long guardId);
}
