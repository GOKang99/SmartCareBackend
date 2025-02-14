package com.smartcarebackend.controller;

import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    // 입소자 등록 API
    @PostMapping("/create")
    public Resident create(ResidentDTO residentDTO) {
        return residentService.createResident(residentDTO);
    }

    // 입소자 수정 API
    @PutMapping("/{resId}")
    public Resident update(ResidentDTO residentDTO, @PathVariable Long resId) {
        return residentService.updateResident(resId, residentDTO);
    }

    // 입소자 삭제 API
    @DeleteMapping("/{resId}")
    public void delete(@PathVariable Long resId) {
        residentService.deleteResident(resId);
    }

    // 입소자 정보 API
    @GetMapping
    public List<Resident> getAll(ResidentDTO residentDTO) {
        System.out.println("모든유저" + residentDTO);
        return residentService.getAllResidents(residentDTO);
    }
}
