package com.smartcarebackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    private static final String UPLOAD_DIR = "public/uploads/"; // 저장할 디렉토리 경로

    public List<String> saveFiles(List<MultipartFile> files) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        if (files == null || files.isEmpty() ) {
            return fileUrls;
        }

        // 디렉토리 없으면 생성
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 랜덤 파일명 생성 (중복 방지)
                String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
                Path filepath = Paths.get(UPLOAD_DIR + fileName);

                // 파일 저장
                Files.write(filepath, file.getBytes());

                // 저장된 파일의 URL 반환
                fileUrls.add("/uploads/" + fileName);
            }
        }
        return fileUrls;
    }

    public void deleteFile(String filePath) {
        if (filePath.startsWith("/uploads/")) {
            filePath = filePath.substring(9); // "/uploads/" 제거
        }

        Path path = Paths.get("public/uploads/" + filePath); // 저장된 이미지 경로
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (deleted) {
                System.out.println("✅ 파일 삭제 완료: " + path);
            } else {
                System.out.println("⚠️ 삭제할 파일이 존재하지 않음: " + path);
            }
        } catch (IOException e) {
            throw new RuntimeException("❌ 파일 삭제 실패: " + filePath, e);
        }

    }
}
