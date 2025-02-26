package com.smartcarebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompositionDTO {

    private Long comId;
    private LocalDate comDate;   // 일자
    private Double comHeight;    // 신장
    private Double comWeight;    // 체중
    private Double comSmm;       // 골격근량
    private Double comBfm;       // 체지방량
    private Double comPbf;       // 체지방율
    private Double comBmi;       // BMI
    private Integer comFatLvl;   // 내장지방레벨

    private Long resId;  // 참조 관계 - resId환자 ID만 전달
    private Long giverId;  // 참조 관계 - Giver의 ID만 전달
}
