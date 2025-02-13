package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
