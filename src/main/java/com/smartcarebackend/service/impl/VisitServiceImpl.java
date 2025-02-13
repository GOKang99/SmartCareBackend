package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.VisitDTO;
import com.smartcarebackend.model.Visit;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.repositories.VisitRepository;
import com.smartcarebackend.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private GuardRepository guardRepository;

    //관리자 기준으로 모든 예약 조회 
    @Override
    public List<VisitDTO> getAllVisits() {
        return visitRepository.findAll()//엔티티에서 다 찾고
                .stream()//하나씩 분리해서
                .map(this::entityToDTO)//엔티티에서 DTO로 변경
                .collect(Collectors.toList());

    }

    //보호자를 기준으로 모든 예약 조회
    @Override
    public List<VisitDTO> getAllVisitsByGuardId(Long guardId) {
        List<Visit> visits = visitRepository.findAllByGuard_GuardId(guardId);
        return visits.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    //보호자+예약 번호로 예약 조회
    @Override
    public VisitDTO getVisitByIdAndGuardId(Long visitId, Long guardId) {
        Visit visit = visitRepository.findByVisIdAndGuard_GuardId(visitId, guardId)
                .orElseThrow(()->new RuntimeException("매칭되는 예약이 없습니다"));

        return entityToDTO(visit);

    }


    // DTO -> Entity
    private Visit dtoToEntity(VisitDTO dto) {
        Visit visit = new Visit();
        // visit.setVisId(dto.getVisId()); // ID는 JPA가 자동 생성 (CREATE 시)
        visit.setVisDate(dto.getVisDate());
        visit.setVisTime(dto.getVisTime());
        visit.setVisTp(dto.getVisTp());
        visit.setVisRelation(dto.getVisRelation());
        visit.setVisCnt(dto.getVisCnt());
        visit.setVisApply(dto.getVisApply());
        visit.setVisYn(dto.getVisYn());
        visit.setRemark(dto.getRemark());
        return visit;
    }

    // Entity -> DTO
    private VisitDTO entityToDTO(Visit visit) {
        VisitDTO dto = new VisitDTO();
        dto.setVisId(visit.getVisId());
        dto.setVisDate(visit.getVisDate());
        dto.setVisTime(visit.getVisTime());
        dto.setVisTp(visit.getVisTp());
        dto.setVisRelation(visit.getVisRelation());
        dto.setVisCnt(visit.getVisCnt());
        dto.setVisApply(visit.getVisApply());
        dto.setVisYn(visit.getVisYn());
        dto.setRemark(visit.getRemark());
        // Guard가 연결되어 있다면 guardId 셋팅
        if (visit.getGuard() != null) {
            dto.setGuardId(visit.getGuard().getGuardId());
        }
        return dto;
    }
}
