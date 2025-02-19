package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.VisitDTO;
import com.smartcarebackend.model.Guard;
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
    public VisitDTO getVisitByIdAndGuardId(Long visId, Long guardId) {
        Visit visit = visitRepository.findByVisIdAndGuard_GuardId(visId, guardId)
                .orElseThrow(()->new RuntimeException("매칭되는 예약이 없습니다"));

        return entityToDTO(visit);

    }

    //예약 생성
    @Override
    public VisitDTO createVisit(Long guardId, VisitDTO visitDTO) {
        //visitDTO 객체를 엔티티로 변환 
      Visit visit = dtoToEntity(visitDTO);
      //받은 guardId를 Guard객체로 변환
      Guard guard = guardRepository.findById(guardId)
              .orElseThrow(()->new RuntimeException(guardId+"해당 Guard가 없습니다."));

      //Visit엔티티의 Guard객체 guardId로 저장 
      visit.setGuard(guard);
      //최종적으로 저장
      Visit savedVisit = visitRepository.save(visit);

      return entityToDTO(savedVisit);
    }

    //예약 수정
    @Override
    public VisitDTO updateVisit(Long visitId, VisitDTO visitDTO) {
        Visit updateVisit = visitRepository.findById(visitId)
                .orElseThrow(()->new RuntimeException(visitId+"해당 예약이 없습니다."));

        updateVisit.setVisTime(visitDTO.getVisTime());
        updateVisit.setVisTp(visitDTO.getVisTp());
        updateVisit.setVisRelation(visitDTO.getVisRelation());
        updateVisit.setVisCnt(visitDTO.getVisCnt());
        updateVisit.setVisApply(visitDTO.getVisApply());
        updateVisit.setVisYn(visitDTO.getVisYn());
        updateVisit.setRemark(visitDTO.getRemark());

        Visit updatedVisit = visitRepository.save(updateVisit);
        return entityToDTO(updatedVisit);
    }

    @Override
    public void deleteVisit(Long visitId) {
           Visit visit =  visitRepository.findById(visitId)
                   .orElseThrow(()->new RuntimeException(visitId+"의 예약을 찾을수 없습니다."));

        visitRepository.delete(visit);
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
