package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "CIS_SPATIAL_TEMPORAL", nullable = false)
    private Long spatialTemporal; //시공간가능 점수:2

    @Column(name = "CIS_EXECUTIVE_FUNCTION", nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id", referencedColumnName = "giverId")
    @ToString.Exclude
    private Giver giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id", referencedColumnName = "resId")
    @ToString.Exclude
    private Resident resident;

}
