package com.smartcarebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CistDTO {
    private Long cisId; // 검사 ID
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime cisDt; // 검사 날짜
    private String cisGrade; // 검사 판정 (정상, 인지저하 의심 등)
    private Long orientation; // 지남력 점수 (5점 만점)
    private Long attention; // 주의력 점수 (3점 만점)
    private Long spatialTemporal; // 시공간 능력 점수 (2점 만점)
    private Long executiveFunction; // 집행기능 점수 (6점 만점)
    private Long memory; // 기억력 점수 (10점 만점)
    private Long language; // 언어 기능 점수 (4점 만점)
    private Long totalScore; // 총합 점수 (30점 만점)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime cisModifyDt; // 수정날짜
    private Long giverId; // 관리자인 Giver의 ID
    private Long residentId; // 검사를 받은 Resident의 ID
}
