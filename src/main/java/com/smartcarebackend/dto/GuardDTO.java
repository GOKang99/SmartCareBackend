package com.smartcarebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuardDTO {

    private String relation;  //환자와의 관계 (예: 부모, 자녀, 배우자 등)

    private String phone;  //휴대폰 번호

    private String ssn;  //주민등록번호 (중복 체크)

    private Long resId;
}
