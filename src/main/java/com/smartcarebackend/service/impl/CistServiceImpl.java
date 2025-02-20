package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.CistDTO;
import com.smartcarebackend.model.Cist;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.repositories.CistRepository;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.service.CistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CistServiceImpl implements CistService {

    private final CistRepository cistRepository;
    private final GiverRepository giverRepository;
    private final ResidentRepository residentRepository;

    public CistServiceImpl(CistRepository cistRepository, GiverRepository giverRepository, ResidentRepository residentRepository) {
        this.cistRepository = cistRepository;
        this.giverRepository = giverRepository;
        this.residentRepository = residentRepository;
    }

    // 총점 계산 메서드
    private Long calculateTotalScore(CistDTO dto) {
        return dto.getOrientation() + dto.getAttention() + dto.getSpatialTemporal() +
                dto.getExecutiveFunction() + dto.getMemory() + dto.getLanguage();
    }
    // 총점 계산 메서드 범위 측정
    private String determineGrade(Long totalScore) {
        if (totalScore >= 25) {
            return "정상";
        } else if (totalScore >= 20) {
            return "주의";
        } else {
            return "인지저하 의심";
        }
    }

    @Override
    @Transactional
    public CistDTO createCist(CistDTO dto) {
        if (dto.getOrientation() == null || dto.getAttention() == null ||
                dto.getSpatialTemporal() == null || dto.getExecutiveFunction() == null ||
                dto.getMemory() == null || dto.getLanguage() == null) {
            throw new IllegalArgumentException("모든 점수 항목을 입력해야 합니다.");
        }

        Cist cist = new Cist();
        cist.setOrientation(dto.getOrientation());
        cist.setAttention(dto.getAttention());
        cist.setSpatialTemporal(dto.getSpatialTemporal());
        cist.setExecutiveFunction(dto.getExecutiveFunction());
        cist.setMemory(dto.getMemory());
        cist.setLanguage(dto.getLanguage());
        cist.setTotalScore(calculateTotalScore(dto));

        cist.setCisGrade(determineGrade(cist.getTotalScore()));

        Giver giver = giverRepository.findById(dto.getGiverId()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Giver ID"));
        Resident resident = residentRepository.findById(dto.getResidentId()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Resident ID"));

        cist.setGiver(giver);
        cist.setResident(resident);
        cist.setCisModifyDt(LocalDateTime.now());

        Cist savedCist = cistRepository.save(cist);
        dto.setCisId(savedCist.getCisId());
        dto.setTotalScore(savedCist.getTotalScore());
        return dto;
    }

    @Override
    @Transactional
    public CistDTO updateCist(Long id, CistDTO dto) {
        Cist cist = cistRepository.findById(id).orElseThrow();
        cist.setOrientation(dto.getOrientation());
        cist.setAttention(dto.getAttention());
        cist.setSpatialTemporal(dto.getSpatialTemporal());
        cist.setExecutiveFunction(dto.getExecutiveFunction());
        cist.setMemory(dto.getMemory());
        cist.setLanguage(dto.getLanguage());
        cist.setTotalScore(calculateTotalScore(dto));
        cist.setCisModifyDt(LocalDateTime.now());

        Cist updatedCist = cistRepository.save(cist); //저장
        // ✅ 수정된 날짜저장
        dto.setCisModifyDt(updatedCist.getCisModifyDt());

        return dto;
    }

    @Override
    @Transactional
    public void deleteCist(Long id) {
        cistRepository.deleteById(id);
    }

    //DTO 변환
    @Override
    public List<CistDTO> getCistByResident(Long residentId) {
        return cistRepository.findByResidentResIdOrderByCisDtDesc(residentId).stream()
                .map(cist -> {
                    CistDTO dto = new CistDTO();
                    dto.setCisId(cist.getCisId());
                    dto.setCisDt(cist.getCisDt());
                    dto.setOrientation(cist.getOrientation());
                    dto.setAttention(cist.getAttention());
                    dto.setSpatialTemporal(cist.getSpatialTemporal());
                    dto.setExecutiveFunction(cist.getExecutiveFunction());
                    dto.setMemory(cist.getMemory());
                    dto.setLanguage(cist.getLanguage());
                    dto.setTotalScore(cist.getTotalScore());
                    dto.setCisGrade(cist.getCisGrade());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}