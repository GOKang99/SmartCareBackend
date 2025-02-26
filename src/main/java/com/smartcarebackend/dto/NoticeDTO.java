package com.smartcarebackend.dto;

import com.smartcarebackend.model.Giver;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
    private String noticeImageUrl;  // 공지 내용 사진 URL
    private MultipartFile noticeImageFile; // 공지사항 이미지 파일
    private String username;
    private Long giverId;
}









