package com.smartcarebackend.controller;

import com.smartcarebackend.dto.VisitDTO;
import com.smartcarebackend.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    //관리자 기준으로 모든 예약 조회
    @GetMapping("/all")
    public List<VisitDTO> getAllVisits() {
        List<VisitDTO> visits = visitService.getAllVisits();
        return visits;
    }
    //보호자 기준으로 모든 예약 조회
    @GetMapping("/all/{guardId}")
    public List<VisitDTO> getAllVisitsByGuardId(@PathVariable Long guardId) {
        List<VisitDTO> visits = visitService.getAllVisitsByGuardId(guardId);
        return visits;
    }
    //보호자 아이디+예약번호로 예약 조회
    @GetMapping("/{visitId}/guard/{guardId}")
    public VisitDTO getVisitByVisIdAndGuardId(@PathVariable Long visitId, @PathVariable Long guardId) {
        VisitDTO visit = visitService.getVisitByIdAndGuardId(visitId, guardId);
        return visit;
    }

    //보호자아이디로 예약 생성하기
    @PostMapping("/create/{guardId}")
    public VisitDTO createVisit(@PathVariable Long guardId, @RequestBody VisitDTO visitDTO) {
        VisitDTO createdVisit = visitService.createVisit(guardId,visitDTO);
        return createdVisit;

    }
}
