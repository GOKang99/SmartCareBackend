package com.smartcarebackend.controller;

import com.smartcarebackend.dto.NoticeDTO;
import com.smartcarebackend.model.Notice;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.NoticeRepository;
import com.smartcarebackend.security.jwt.JwtUtils;
import com.smartcarebackend.service.NoticeService;
import com.smartcarebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    
    // 모든 공지사항 조회 (최신순)
    @GetMapping
    public List<NoticeDTO> getAllNotices() {
        List<Notice> notices = noticeService.getAllNotices();
//        System.out.println("확인" + notices.stream().map(this::convertToDTO).collect(Collectors.toList()));
        return notices.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 공지사항 작성 (ADMIN 만 가능)
   @PostMapping("/create")
   public String createNotice(@RequestBody NoticeDTO noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return "공지사항 작성 성공";
   }

   // 공지사항 상세보기
   @GetMapping("/{noticeId}")
   public ResponseEntity<NoticeDTO> getNotice(@PathVariable Long noticeId) {
        Notice notice = noticeService.getNoticeById(noticeId);
        NoticeDTO noticeDTO=convertToDTO(notice);
//        System.out.println("한글"+noticeDTO);
        return ResponseEntity.ok(noticeDTO);
    }

    // Notice 엔티티를 NoticeDTO로 변환하는 메소드
    private NoticeDTO convertToDTO(Notice notice) {
        Long giverId = null;
        if (notice.getGiver() != null) {
            giverId = notice.getGiver().getGiverId();
        }
        return new NoticeDTO(
                notice.getNoticeId(),
                notice.getNoticeType(),
                notice.getNoticeTitle(),
                notice.getNoticeContent(),
                notice.getNoticeDate(),
                notice.getNoticeUpdate(),
                notice.getNoticeCount(),
                notice.getNoticeImageUrl(),
                null,
                notice.getGiver().getUser().getUsername(),
                giverId
        );
    }
}
