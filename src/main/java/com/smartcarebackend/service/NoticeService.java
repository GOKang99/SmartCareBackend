package com.smartcarebackend.service;

import com.smartcarebackend.dto.NoticeDTO;
import com.smartcarebackend.model.Notice;

import java.util.List;

public interface NoticeService {
    // 공지사항 목록을 가져오는 메소드
List<Notice> getAllNotices();

void createNotice(NoticeDTO noticeDTO);

    Notice getNoticeById(Long noticeId);
}
