package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.NoticeDTO;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Notice;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.NoticeRepository;
import com.smartcarebackend.repositories.UserRepository;
import com.smartcarebackend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GiverRepository giverRepository;

    // 공지사항 목록을 날짜 기준 내림차순으로 정렬하여 가져오기
    @Override
    public List<Notice> getAllNotices() {
        return noticeRepository.findAllByOrderByNoticeDateDesc();
    }

    // 공지사항 상세보기
    @Override
    public Notice getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다." + noticeId));
    }

    @Override
    public void createNotice(NoticeDTO noticeDTO) {

        Notice notice = new Notice();
        notice.setNoticeType(noticeDTO.getNoticeType());
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());
        notice.setNoticeContent(noticeDTO.getNoticeContent());
        notice.setNoticeDate(LocalDateTime.now());
        notice.setNoticeId(noticeDTO.getNoticeId());

        //로그인한 유저 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // 현재 로그인한 유저의 username
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found" + username));

        // 유저의 giver 가져오기
        if (user.getGiver() != null) {
            notice.setGiver(user.getGiver());
        } else {
            throw new RuntimeException("User not found" + username);
        }

        noticeRepository.save(notice);
    }
}
