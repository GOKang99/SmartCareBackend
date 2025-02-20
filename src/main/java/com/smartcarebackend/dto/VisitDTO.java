package com.smartcarebackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class VisitDTO {
    private Long visId;        // 업데이트/조회 시 사용
    private LocalDate visDate;
    private LocalTime visTime;
    private String visTp;
    private String visRelation;
    private int visCnt;
    private String visApply;
    private Boolean visYn;
    private String remark;

    // Guard 연동을 위한 ID
    private Long guardId; //guardId만 담아서 Service에서 리파짓토리로 찾아옴
}
