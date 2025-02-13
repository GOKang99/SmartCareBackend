package com.smartcarebackend.controller;

import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    // 입소자 등록 API
    @PostMapping("/create")
    public Resident create(ResidentDTO residentDTO) {

        System.out.println("디티오: " + residentDTO);
        return residentService.createResident(residentDTO);

    }

    // 입소자 삭제 API
    @DeleteMapping("/{resId}")
    public void delete(@PathVariable Long resId) {
        System.out.println("삭제" + resId);
        residentService.deleteResident(resId);
    }
}
