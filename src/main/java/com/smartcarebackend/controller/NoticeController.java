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

import java.util.ArrayList;
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
        return notices.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 공지사항 작성 (ADMIN 만 가능)
    @PostMapping("/create")
    public ResponseEntity<String> createNotice(@ModelAttribute NoticeDTO noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return ResponseEntity.ok("공지사항 작성 성공");
    }

    // 공지사항 상세보기
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable Long noticeId) {
        Notice notice = noticeService.getNoticeById(noticeId);
        NoticeDTO noticeDTO = convertToDTO(notice);
        System.out.println("공지사항데이터: "+noticeDTO);
        return ResponseEntity.ok(noticeDTO);
    }


    // 공지사항 수정하기
    @PutMapping("/edit/{noticeId}")
    public ResponseEntity<String> updateNotice(@PathVariable Long noticeId,
                                               @ModelAttribute NoticeDTO noticeDTO) {
        boolean updated = noticeService.updateNotice(noticeId, noticeDTO);
        if(updated) {
            return ResponseEntity.ok("공지사항이 수정되었습니다");
        } else {
            return ResponseEntity.status(404).body("해당 공지사항을 찾을 수 없습니다");
        }
    }

    // 공지사항 삭제하기
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long noticeId) {
        boolean deleted = noticeService.deleteNotice(noticeId);
        if (deleted) {
            return ResponseEntity.ok("공지사항이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(404).body("해당 공지사항을 찾을 수 없습니다.");
        }
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
                notice.getNoticeImageUrls(),
                null,
                notice.getGiver().getUser().getUsername(),
                giverId,
                new ArrayList<>()
        );
    }
}
