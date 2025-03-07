package com.smartcarebackend.service;

import com.smartcarebackend.dto.CompositionDTO;

import java.util.List;

public interface CompositionService {

    void deleteComposition(Long comId);

    List<CompositionDTO> getCompositionsByResId(Long resId);

    CompositionDTO createCompositions(Long resId, Long giverId, CompositionDTO compositionDTO);

    CompositionDTO updateComposition(Long comId, Long updatedBy, CompositionDTO compositionDTO);

    //보호자와 연결된 환자의 체성분 리스트 가져오기
    List<CompositionDTO> getStatusCompositionByGuardId(Long guardId);

}
