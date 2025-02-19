package com.smartcarebackend.controller;

import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    // 입소자 등록 API
    @PostMapping("/create")
    public Resident create(Long giverId, ResidentDTO residentDTO) {
        return residentService.createResident(giverId, residentDTO);
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
        return residentService.getAllResidents(residentDTO);
    }

        // 입소자 상세 정보 API
        @GetMapping("/{resId}")
        public Resident getResidentById(@PathVariable Long resId) {
            return residentService.getResidentById(resId);
        }
}
