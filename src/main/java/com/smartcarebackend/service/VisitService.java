package com.smartcarebackend.service;

import com.smartcarebackend.dto.VisitDTO;
import java.util.List;


public interface VisitService {
    //모든 예약 조회 (관리자용)
    List<VisitDTO> getAllVisits();
    //Guard아이디,보호자아이디로 예약 조회
    List<VisitDTO> getAllVisitsByGuardId(Long guardId);

    //보호자+예약번호로 조회
    VisitDTO getVisitByIdAndGuardId(Long visId, Long guardId);
    //예약 생성
    VisitDTO createVisit(Long guardId, VisitDTO visitDTO);

    //예약 업데이트
    VisitDTO updateVisit(Long visitId, VisitDTO visitDTO);
    //예약 지우기
    void deleteVisit(Long visitId);
}
