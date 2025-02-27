package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 공지사항을 날짜 기준 내림차순으로 정렬하여 조회
    List<Notice> findAllByOrderByNoticeDateDesc();
}
