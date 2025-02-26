package com.smartcarebackend.service.impl;


import com.smartcarebackend.dto.CompositionDTO;
import com.smartcarebackend.model.Composition;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.repositories.CompositionRepository;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.service.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompositionImpl implements CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private GiverRepository giverRepository;



    //관리자가 환자 번호로 모든 예약 조회
    @Override
    public List<CompositionDTO> getCompositionsByResId(Long resId) {
        List<Composition> compositions = compositionRepository.findAllByResident_ResId(resId);
        return compositions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    //관리자가 환자 Id를 가지고 체성분 분석 자료  생성
    @Override
    public CompositionDTO createCompositions(Long resId, Long giverId, CompositionDTO compositionDTO) {
        //프론트에서 받아온 체성분 분석 자료 엔티티로 변환
        Composition composition = toEntity(compositionDTO);
        //받아온 환자 Id를 통해서 환자 찾기
        Resident resident = residentRepository.findById(resId)
                .orElseThrow(()->new RuntimeException("예약 생성시 환자 Id에 따른 환자가 없습니다."));
        //받아온 요양보호사 Id를 통해서 등록하는 요양보호사 찾기.
        Giver giver = giverRepository.findById(giverId)
                .orElseThrow(()->new RuntimeException("예약 생성시 요양보호사Id에 따른 환자를 찾을수 없습니다. "));

        //체성분 분석 엔티티에 환자 정보 등록.
        composition.setResident(resident);
        //체성분 분석 엔티티에 요양보호사 정보 등록.
        composition.setGiver(giver);
        //DB에 저장하기
        Composition savedComposition = compositionRepository.save(composition);
        return toDTO(savedComposition);

    }

    //compostionId로 삭제하기
    @Override
    public void deleteComposition(Long comId) {
        compositionRepository.deleteById(comId);

    }



    // Entity -> DTO 변환
    public  CompositionDTO toDTO(Composition composition) {
        return new CompositionDTO(
                composition.getComId(),
                composition.getComDate(),
                composition.getComHeight(),
                composition.getComWeight(),
                composition.getComSmm(),
                composition.getComBfm(),
                composition.getComPbf(),
                composition.getComBmi(),
                composition.getComFatLvl(),
                composition.getResident() != null ? composition.getResident().getResId() : null,
                composition.getGiver() != null ? composition.getGiver().getGiverId() : null
        );
    }

    // DTO -> Entity 변환
    public Composition toEntity(CompositionDTO dto) {
        Composition composition = new Composition();
        composition.setComId(dto.getComId());
        composition.setComDate(dto.getComDate());
        composition.setComHeight(dto.getComHeight());
        composition.setComWeight(dto.getComWeight());
        composition.setComSmm(dto.getComSmm());
        composition.setComBfm(dto.getComBfm());
        composition.setComPbf(dto.getComPbf());
        composition.setComBmi(dto.getComBmi());
        composition.setComFatLvl(dto.getComFatLvl());
        return composition;
    }
}
