package com.smartcarebackend.service;

import com.smartcarebackend.dto.CompositionDTO;

import java.util.List;

public interface CompositionService {

    void deleteComposition(Long comId);

    List<CompositionDTO> getCompositionsByResId(Long resId);

    CompositionDTO createCompositions(Long resId, Long giverId, CompositionDTO compositionDTO);
}
