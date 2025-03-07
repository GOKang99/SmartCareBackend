package com.smartcarebackend.controller;

import com.smartcarebackend.dto.CistDTO;

import com.smartcarebackend.dto.MealDTO;
import com.smartcarebackend.model.Cist;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.repositories.CistRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.model.Guard;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.service.CistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cist")
@CrossOrigin(origins = "*")
public class CistController {

    @Autowired
    private CistService cistService;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private GuardRepository guardRepository;

    @Autowired
    private CistRepository cistRepository;

    // ✅ 특정 대상자의 검사 기록을 리스트로 조회 (테이블용)
    @GetMapping("/list/{residentId}")
    public ResponseEntity<List<CistDTO>> getCistList(@PathVariable Long residentId) {
        return ResponseEntity.ok(cistService.getCistByResident(residentId));
    }

    //생활현황의 cist검사 데이터 가져오기
    @GetMapping("/status/{guardId}")
    public ResponseEntity<List<CistDTO>> getStatusForCists(@PathVariable Long guardId) {
        //guardId로 CistDTO 리스트 가져오기
        List<CistDTO> cistDTOList = cistService.getStatusCistByGuardId(guardId);
        return  ResponseEntity.ok(cistDTOList);
    }

    // ✅ 특정 대상자의 검사 기록을 조회 (그래프 데이터 제공)
    @GetMapping("/graph/{residentId}")
    public ResponseEntity<List<Map<String, Object>>> getGraphData(@PathVariable Long residentId) {
        List<CistDTO> cistList = cistService.getCistByResident(residentId);
        if (cistList.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); //빈리스트로 반환
        }
        List<Map<String, Object>> response = new ArrayList<>();
        for (CistDTO cist : cistList) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("date", cist.getCisDt());
            dataMap.put("totalScore", cist.getTotalScore());
            dataMap.put("orientation", cist.getOrientation());
            dataMap.put("attention", cist.getAttention());
            dataMap.put("spatialTemporal", cist.getSpatialTemporal());
            dataMap.put("executiveFunction", cist.getExecutiveFunction());
            dataMap.put("memory", cist.getMemory());
            dataMap.put("language", cist.getLanguage());
            response.add(dataMap);
        }
        return ResponseEntity.ok(response);
    }

    // 검사 환자 조회
    @GetMapping("/admin")
    public ResponseEntity<List<CistDTO>> getAllCistsForAdmin() {
        // 관리자가 모든 식사 일지를 조회하는 서비스 호출
        List<CistDTO> cistDTOs = cistService.getAllCistsForAdmin();
        System.out.println("cistDTO의 위치는"+cistDTOs);
        return ResponseEntity.ok(cistDTOs); // 조회된 데이터를 200 OK와 함께 반환
    }

    // ✅ Cist 추가
    @PostMapping("/admin")
    public ResponseEntity<CistDTO> createCist(@RequestBody CistDTO cistDTO) {
        // 필수 값 검증
        if (cistDTO.getResidentId() == null) {
            throw new IllegalArgumentException("Resident ID가 없습니다.");
        }
        if (cistDTO.getCisDt() == null) {
            throw new IllegalArgumentException("CIST 날짜가 없습니다.");
        }

        CistDTO createdCist = cistService.createCist(cistDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCist);  // 201 Created 상태 코드 사용
    }

    // ✅ Cist 수정
    @PutMapping("/admin/{cisId}")
    public ResponseEntity<CistDTO> updateCist(@PathVariable Long cisId, @RequestBody CistDTO cistDTO) {
        CistDTO updatedCist = cistService.updateCist(cisId, cistDTO);
        return ResponseEntity.ok(updatedCist);
    }

    // ✅ Cist 삭제
    @DeleteMapping("/admin/{cisId}")
    public ResponseEntity<Void> deleteCist(@PathVariable Long cisId) {
        cistService.deleteCist(cisId);
        return ResponseEntity.noContent().build();
    }
    // 레지던트 찾아오기
    @GetMapping("/admin/residents")
    public ResponseEntity<List<Resident>> getAllMResidentsForAdmin() {
        List<Resident> residents = residentRepository.findAll();
        return ResponseEntity.ok(residents);
    }
}
