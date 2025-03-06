package com.smartcarebackend.controller;

import com.smartcarebackend.dto.CompositionDTO;
import com.smartcarebackend.model.Composition;
import com.smartcarebackend.service.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/composition")
public class CompositionController {

    @Autowired
    private CompositionService compositionService;

    //환자 아이디로 찾아오기
    @GetMapping("/all/{resId}")
    public List<CompositionDTO> getAllCompositions(@PathVariable Long resId) {
        List<CompositionDTO> compositionsForResId = compositionService.getCompositionsByResId(resId);
        return compositionsForResId;
    }

    //요양보호사가 환자Id를 통해서 체성분 분석(건강검진) 등록하기
    @PostMapping("/create/{resId}/{giverId}")
    public CompositionDTO createComposition(@PathVariable Long resId,@PathVariable Long giverId ,@RequestBody CompositionDTO compositionDTO) {
        System.out.println("넘어온resId"+ resId);
        System.out.println("넘어온resId"+ giverId);
        System.out.println("넘어온 DTO"+compositionDTO);
        CompositionDTO createdComposition = compositionService.createCompositions(resId,giverId,compositionDTO);
        return createdComposition;
    }

//    지우기
    @DeleteMapping("/delete/{comId}")
    public void deleteComposition(@PathVariable Long comId) {
        compositionService.deleteComposition(comId);
    }

    //모든 체성분 분석 가져오기

    //수정하기
    @PutMapping("/update/{comId}/{updatedBy}")
    public CompositionDTO updateComposition(@PathVariable Long comId,@PathVariable Long updatedBy,@RequestBody CompositionDTO compositionDTO) {
        return compositionService.updateComposition(comId, updatedBy, compositionDTO);
    }

}

