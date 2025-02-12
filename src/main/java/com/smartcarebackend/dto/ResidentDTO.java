package com.smartcarebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentDTO {
    private Long redId;

    private Long giverId;

    private String resName; // 이름

    private String resGenter; // 성별

    private LocalDate resBirth; // 생년월일

    private String resPhone; // 전화번호

    private String resGrade; // 등급

    private boolean dementiaYn; // 치매 유무

    private boolean fallYn; // 낙상 위험

    private boolean bedsoreYn; // 욕창 위험

    private boolean postureYn; // 자세변경

    private String resDisease; // 주요질환

    private String resLocation; // 생활실

    private LocalDate resEnterDate; // 입소일

    private LocalDate resExitDate; // 퇴소일

    private String resAddress; // 주소

    private String systemResCode; // 요양시스템 입소자 코드

    private String resSchoolGrade; // 최종학력

    private String resLongTermCareNo; // 장기요양인정번호

    private String resCareGroup; // 케어그룹

    private String resFoodType; // 식사종류

    private String resFunctionDis; // 기능장애

    private MultipartFile resImages; // 입소자 사진
}
