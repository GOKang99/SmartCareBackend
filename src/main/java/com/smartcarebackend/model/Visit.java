package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIS_ID" )
    private Long visId; //예약 등록 기본 키

    @Column(name = "GIVER_ID")
    private String giverId; // 요양사 ID

    @Column(name = "GUARD_ID")
    private Long guardId; // 보호자 ID

    @Column(name = "RES_ID")
    private Long resId; // 입소자 ID

    @Column(name = "VIS_Date", nullable = false)
    private LocalDate visDate; // 일자

    @Column(name = "VIS_TIME", nullable = false)
    private LocalTime visTime; // 예약 시간

    @Column(name = "VIS_TP", nullable = false)
    private String visTp; // 예약 유형 (방문, 영상통화) 방문:visit 영상통화:face

    @Column(name = "VIS_RELATION", nullable = false, length = 50)
    private String visRelation; // 어르신과의 관계

    @Column(name = "VIS_CNT", nullable = false)
    private int visCnt; // 방문자수

    @Column(name = "VIS_APPLY", nullable = false)
    private String visApply; //승인상태 기본값: 대기 pending 허가:permited 거절:rejected

    @Column(name = "VIS_YN", nullable = false)
    private Boolean visYn; //방문여부 방문하였다면 true 미방문시 false 기본 값:false

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 예약 생성 시간 (자동 설정)

    @Column(name = "REMARK", columnDefinition = "TEXT")
    private String remark; // 비고 비고 수정
}
