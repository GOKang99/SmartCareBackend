package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long noticeId; // 공지사항 고유 ID

    @Column(name = "GIVER_ID", nullable = false)
    private String giverId; // 공지를 작성한 사람의 ID
    
    @Column(name = "NOTICE_TP", nullable = false, length = 20)
    private String noticeType; // 공지 유형 (식단, 계획표, 진료일정)
    
    @Column(name = "NOTICE_TITLE", nullable = false, length = 200)
    private String noticeTitle; // 공지 제목
    
    @Column(name = "NOTICE_CONTENT", nullable = false, columnDefinition = "TEXT")
    private String noticeContent; // 공지 내용
    
    @Column(name = "NOTICE_DATE", nullable = false)
    private LocalDateTime noticeDate; // 공지 작성 날짜

    @Column(name = "NOTICE_UPDATE")
    private LocalDateTime noticeUpdate; // 공지 수정 날짜

    @Column(name = "NOTICE_COUNT", nullable = false)
    private int noticeCount = 0; // 공지 조회수 (기본값 : 0 )
}
