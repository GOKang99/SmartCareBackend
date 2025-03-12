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
    private String cisDt; //검사일자

    @Column(name = "CIS_GRADE", length = 20, nullable = false)
    private String cisGrade; // 검사 판정 (심각, 보통, 정상)

    @Column(name = "CIS_ORIENTATION", length = 10)
    private Long orientation; //지남력 점수:5

    @Column(name = "CIS_ATTENTION", length = 10)
    private Long attention; //주의력 점수:3

    @Column(name = "CIS_SPATIAL_TEMPORAL", length = 10)
    private Long spatialTemporal; //시공간가능 점수:2

    @Column(name = "CIS_EXECUTIVE_FUNCTION", length = 10)
    private Long executiveFunction; //집행기능 점수:6

    @Column(name = "CIS_MEMORY", length = 10)
    private Long memory; //기억력 점수:10

    @Column(name = "CIS_LANGUAGE", length = 10)
    private Long language; //언어기능 점수:4

    @Column(name = "CIS_TOTALSCORE", length = 10)
    private Long totalScore; //총합계점수 점수:30

    @CreationTimestamp
    @Column(name = "CIS_MODIFY_DT")
    private String cisModifyDt; // 수정 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id", referencedColumnName = "giverId")
    @ToString.Exclude
    private Giver giver; //관리자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id", referencedColumnName = "resId")
    @ToString.Exclude
    private Resident resident;  //환자

}
