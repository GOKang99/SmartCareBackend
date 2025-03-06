package com.smartcarebackend.controller;

import com.smartcarebackend.dto.CistDTO;
import com.smartcarebackend.model.Guard;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.service.CistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cist")
public class CistController {
    @Autowired
    private CistService cistService;

    @Autowired
    private GuardRepository guardRepository;

    //생활현황의 cist검사 데이터 가져오기(/api/cist)
    @GetMapping
    public ResponseEntity<List<CistDTO>> getStatusCists(@PathVariable Long guardId) {
        Guard getGuard = guardRepository.findById(guardId).orElseThrow(()-> new RuntimeException("보호자 정보를 찾을 수 없습니다."));
        return  null;
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

    // ✅ Cist 추가
    @PostMapping
    public ResponseEntity<CistDTO> createCist(@RequestBody CistDTO cistDTO) {
        CistDTO createdCist = cistService.createCist(cistDTO);
        return ResponseEntity.ok(createdCist);
    }

    // ✅ Cist 수정
    @PutMapping("/{cistId}")
    public ResponseEntity<CistDTO> updateCist(@PathVariable Long cistId, @RequestBody CistDTO cistDTO) {
        CistDTO updatedCist = cistService.updateCist(cistId, cistDTO);
        return ResponseEntity.ok(updatedCist);
    }

    // ✅ Cist 삭제
    @DeleteMapping("/{cistId}")
    public ResponseEntity<Void> deleteCist(@PathVariable Long cistId) {
        cistService.deleteCist(cistId);
        return ResponseEntity.noContent().build();
    }
}
