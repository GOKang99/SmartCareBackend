package com.smartcarebackend.controller;

import com.smartcarebackend.model.Visit;
import com.smartcarebackend.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping("/all") //모든 예약 현황 가지고 오기
    public List<Visit> getAllVisits() {
        return visitService.getAllVisits();
    }

    @GetMapping("/{guardId}") //보호자 화면에 표시 되는 코드
    public List<Visit> getMyVisit(@PathVariable Long guardId) {
        return visitService.getVisitsByGuardId(guardId);
    }
}
