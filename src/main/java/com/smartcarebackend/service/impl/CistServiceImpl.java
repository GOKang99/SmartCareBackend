package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.CistDTO;
import com.smartcarebackend.model.Cist;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.repositories.CistRepository;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.service.CistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CistServiceImpl implements CistService {

    @Autowired
    CistRepository cistRepository;
    @Autowired
    GiverRepository giverRepository;
    @Autowired
    ResidentRepository residentRepository;

    // 총점 계산 메서드
    private Long calculateTotalScore(CistDTO dto) {
        return dto.getOrientation() + dto.getAttention() + dto.getSpatialTemporal() +
                dto.getExecutiveFunction() + dto.getMemory() + dto.getLanguage();
    }

    // 총점 계산 메서드 범위 측정
    private String determineGrade(Long totalScore) {
        if (totalScore >= 25) return "정상";
        else if (totalScore >= 20) return "주의";
        return "인지저하 의심";
    }
    // cist그래프 생성
    @Override
    @Transactional
    public CistDTO createCist(CistDTO dto) {

        //점수 항목이 모두 입력되었는지체크
        if (dto.getOrientation() == null || dto.getAttention() == null ||
                dto.getSpatialTemporal() == null || dto.getExecutiveFunction() == null ||
                dto.getMemory() == null || dto.getLanguage() == null) {
            throw new IllegalArgumentException("모든 점수 항목을 입력해야 합니다.");
        }

        //cist 객체 생성 후 데이터 설정
        Cist cist = new Cist();
        cist.setOrientation(dto.getOrientation());
        cist.setAttention(dto.getAttention());
        cist.setSpatialTemporal(dto.getSpatialTemporal());
        cist.setExecutiveFunction(dto.getExecutiveFunction());
        cist.setMemory(dto.getMemory());
        cist.setLanguage(dto.getLanguage());
        cist.setTotalScore(calculateTotalScore(dto));

        cist.setCisGrade(determineGrade(cist.getTotalScore()));

        // Giver 및 Resident 객체 설정
        Giver giver = giverRepository.findById(dto.getGiverId()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Giver ID"));
        Resident resident = residentRepository.findById(dto.getResidentId()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Resident ID"));

        cist.setGiver(giver);
        cist.setResident(resident);
        cist.setCisModifyDt(LocalDateTime.now().toString());  // 날짜를 String 형식으로 설정

        // Cist 저장 후 반환
        Cist savedCist = cistRepository.save(cist);
        dto.setCisId(savedCist.getCisId());
        dto.setTotalScore(savedCist.getTotalScore());
        return dto;
    }
    //cist 수정
    @Override
    @Transactional
    public CistDTO updateCist(Long id, CistDTO dto) {

        // 기존 Cist 조회 후 수정
        Cist cist = cistRepository.findById(id).orElseThrow();
        cist.setOrientation(dto.getOrientation());
        cist.setAttention(dto.getAttention());
        cist.setSpatialTemporal(dto.getSpatialTemporal());
        cist.setExecutiveFunction(dto.getExecutiveFunction());
        cist.setMemory(dto.getMemory());
        cist.setLanguage(dto.getLanguage());
        cist.setTotalScore(calculateTotalScore(dto));
        cist.setCisModifyDt(LocalDateTime.now().toString()); // 날짜를 String 형식으로 설정

        // 수정된 Cist 저장 후 반환
        Cist updatedCist = cistRepository.save(cist); // 저장
        dto.setCisModifyDt(updatedCist.getCisModifyDt());

        return dto;
    }
    //cist 삭제
    @Override
    @Transactional
    public void deleteCist(Long id) {
        cistRepository.deleteById(id);
    }
    //측정 대상자의 cist  조회
    @Override
    public List<CistDTO> getCistByResident(Long residentId) {

        // 해당 residentId에 대한 Cist 리스트 조회 후 DTO로 변환
        return cistRepository.findByResidentResIdOrderByCisDtDesc(residentId).stream()
                .map(cist -> {
                    CistDTO dto = new CistDTO();
                    dto.setCisId(cist.getCisId());
                    dto.setCisDt(cist.getCisDt());
                    dto.setCisGrade(cist.getCisGrade());
                    dto.setOrientation(cist.getOrientation());
                    dto.setAttention(cist.getAttention());
                    dto.setSpatialTemporal(cist.getSpatialTemporal());
                    dto.setExecutiveFunction(cist.getExecutiveFunction());
                    dto.setMemory(cist.getMemory());
                    dto.setLanguage(cist.getLanguage());
                    dto.setTotalScore(cist.getTotalScore());
                    dto.setCisModifyDt(cist.getCisModifyDt());

                    // Resident와 Giver 정보 추가
                    if (cist.getResident() != null) {
                        dto.setResidentId(cist.getResident().getResId());
                        dto.setResName(cist.getResident().getResName());
                    }

                    if (cist.getGiver() != null) {
                        dto.setGiverId(cist.getGiver().getGiverId());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
}