package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.NoticeDTO;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Notice;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.NoticeRepository;
import com.smartcarebackend.repositories.UserRepository;
import com.smartcarebackend.service.FileService;
import com.smartcarebackend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FileService fileService; 

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
    public boolean deleteNotice(Long noticeId) {
        if(noticeRepository.existsById(noticeId)) {
            noticeRepository.deleteById(noticeId);
            return true;
        }
        return false;
    }
    
    // 공지사항 수정
    @Override
    public boolean updateNotice(Long noticeId, NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(()->new RuntimeException("공지사항을 찾을 수 없습니다. ID: " + noticeId));
        
        // 공지사항 제목 및 내용 업데이트
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());
        notice.setNoticeContent(noticeDTO.getNoticeContent());
        notice.setNoticeUpdate(LocalDateTime.now());
        notice.setNoticeType(noticeDTO.getNoticeType());

        //기존 이미지 리스트 가져오기
        List<String> existingFileUrls = notice.getNoticeImageUrls();
        if(existingFileUrls == null) {
            existingFileUrls = new ArrayList<>();
        }

        // ✅ 삭제할 이미지가 있다면 실제 파일 삭제 및 DB에서도 제거
        if (noticeDTO.getDeletedImages() != null && !noticeDTO.getDeletedImages().isEmpty()) {
            for (String imageUrl : noticeDTO.getDeletedImages()) {
                fileService.deleteFile(imageUrl);
                existingFileUrls.remove(imageUrl);
            }
        }

        // 새이미지 저장
        try {
            if (noticeDTO.getNoticeImageFiles() != null && !noticeDTO.getNoticeImageFiles().isEmpty()) {
                List<String> newFileUrls = fileService.saveFiles(noticeDTO.getNoticeImageFiles());
                existingFileUrls.addAll(newFileUrls);
            }
        }catch (IOException e){
            throw new RuntimeException("파일 저장 실패", e);
        }
        notice.setNoticeImageUrls(existingFileUrls); // 수정된 이미지 리스트 저장
        noticeRepository.save(notice);
        return true;
    }
    
    // 공지사항 생성
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

        //파일 저장 및 URL 리스트 반환
        try {
            List<String> fileUrls = fileService.saveFiles(noticeDTO.getNoticeImageFiles());
            notice.setNoticeImageUrls(fileUrls); // 파일URL저장
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        noticeRepository.save(notice);
    }
}
