package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Cist {
    @Id
    @Column(name = "CIS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cisId; //검사 아이디

    @CreationTimestamp
    @Column(name = "CIS_DT", nullable = false)
    private LocalDateTime cisDt; //검사일자

    @Column(name = "CIS_GRADE", length = 10)
    private String cisGrade; // 검사 판정 (심각, 보통, 정상)

    @Column(name = "CIS_ORIENTATION", nullable = false)
    private Long orientation; //지남력 점수:5

    @Column(name = "CIS_ATTENTION", nullable = false)
    private Long attention; //주의력 점수:3

    @Column(name = "CIS_SPATIAL-TEMPORAL", nullable = false)
    private Long spatialTemporal; //시공간가능 점수:2

    @Column(name = "CIS_EXECUTIVE-FUNCTION", nullable = false)
    private Long executiveFunction; //집행기능 점수:6

    @Column(name = "CIS_MEMORY", nullable = false)
    private Long memory; //기억력 점수:10

    @Column(name = "CIS_LANGUAGE", nullable = false)
    private Long language; //언어기능 점수:4

    @Column(name = "CIS_TOTALSCORE", nullable = false)
    private Long totalScore; //총합계점수 점수:30

    @CreationTimestamp
    @Column(name = "CIS_MODIFY_DT", nullable = false)
    private LocalDateTime cisModifyDt; // 수정 날짜

    // 총점을 계산하고 판정을 업데이트하는 메서드
    public void calculateTotalAndGrade() {
        // 총점 계산
        this.totalScore = this.orientation + this.attention + this.spatialTemporal
                            + this.executiveFunction + this.memory + this.language;

        // 판정 기준에 맞는 판정 부여
        if (this.totalScore <= 10) {
            this.cisGrade = "심각";
        } else if (this.totalScore <= 24) {
            this.cisGrade = "보통";
        } else {
            this.cisGrade = "정상";
        }
    }

}
