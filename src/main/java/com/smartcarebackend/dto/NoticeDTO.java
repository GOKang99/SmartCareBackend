package com.smartcarebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {

    private Long noticeId; // 공지사항 고유 ID
    private String noticeType; // 공지 유형 (식단, 계획표, 진료일정)
    private String noticeTitle; // 공지 제목
    private String noticeContent; // 공지 내용
    private LocalDateTime noticeDate; // 공지 작성 날짜
    private LocalDateTime noticeUpdate; // 공지 수정 날짜
    private int noticeCount; // 공지 조회수
    private List<String> noticeImageURL; // DB 저장용, 여러개의 이미지 URL 저장
    private List<MultipartFile> noticeImageFiles; // 업로드할 파일 리스트
    private String username;
    private String giverName;
    private Long giverId;

    private List<String> deletedImages;


}









