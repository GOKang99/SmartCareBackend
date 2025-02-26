package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.dto.GuardDTO;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.repositories.UserRepository;
import com.smartcarebackend.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;


@Service
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private GiverRepository giverRepository;

    @Autowired
    private GuardRepository guardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ResidentServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    // systemResCode 자동 생성 메서드
    private String generateSystemResCode() {
        // 예: "20250001" 형태로 생성
        Long lastResidentId = residentRepository.findTopByOrderByResIdDesc()
                .map(Resident::getResId)
                .orElse(0L);

        String prefix = "2025"; // 특정 연도나 규칙을 넣을 수 있음
        String newCode = prefix + String.format("%04d", lastResidentId + 1); // 예시: "20250001"
        return newCode;
    }

    // 입소자 정보 등록
    @Override
    public Resident createResident(Long giverId, ResidentDTO residentDTO) {

        MultipartFile resImages = residentDTO.getResImages();

        Date createDate = new Date();
        String resFileName = createDate.getTime() + "_" + resImages.getOriginalFilename();


        try {
            String uploadImages = "public/images/";
            Path uploadImagesPath = Paths.get(uploadImages);

            if (!Files.exists(uploadImagesPath)) {
                Files.createDirectory(uploadImagesPath);
            }
            try (InputStream inputStream = resImages.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadImages + resFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Giver 객체를 giverId로 찾아서 할당
        Giver giver = giverRepository.findById(giverId)
                .orElseThrow(() -> new IllegalArgumentException("Giver not found for ID: " + residentDTO.getGiverId()));


        Resident resident = new Resident();
        resident.setGiver(giver); // 요양보호사 ID
        resident.setResName(residentDTO.getResName()); // 이름
        resident.setResGender(residentDTO.getResGender()); // 성별
        resident.setResBirth(residentDTO.getResBirth());
        ; // 생년월일
        resident.setResPhone(residentDTO.getResPhone()); // 전화번호
        resident.setResGrade(residentDTO.getResGrade()); // 등급
        resident.setDementiaYn(residentDTO.isDementiaYn()); // 치매 유무
        resident.setFallYn(residentDTO.isFallYn()); // 낙상 위험
        resident.setBedsoreYn(residentDTO.isBedsoreYn()); // 욕창 위험
        resident.setPostureYn(residentDTO.isPostureYn()); // 자세변경
        resident.setResDisease(residentDTO.getResDisease()); // 주요질환
        resident.setResLocation(residentDTO.getResLocation()); // 생활실
        resident.setResEnterDate(residentDTO.getResEnterDate()); // 입소일
        resident.setResExitDate(residentDTO.getResExitDate()); // 퇴소일
        resident.setResAddress(residentDTO.getResAddress()); // 주소
        String generatedCode = generateSystemResCode();
        resident.setSystemResCode(generatedCode);
        resident.setResSchoolGrade(residentDTO.getResSchoolGrade()); // 최종학력
        resident.setResLongTermCareNo(residentDTO.getResLongTermCareNo()); // 장기요양인정번호
        resident.setResCareGroup(residentDTO.getResCareGroup()); // 케어그룹
        resident.setResFoodType(residentDTO.getResFoodType()); // 식사종류
        resident.setResFunctionDis(residentDTO.getResFunctionDis()); // 기능장애

        resident.setResImageAddress(resFileName); // 이미지 주소

        return residentRepository.save(resident);
    }

    @Override
    public Resident updateResident(Long redId, ResidentDTO residentDTO) {
        Resident resident = residentRepository.findById(redId).get();
        if (residentDTO.getResImages() != null) {
            String uploadImages = "public/images/";
            Path oldImagePath = Paths.get(uploadImages + resident.getResImageAddress());
            try {
                Files.delete(oldImagePath);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            MultipartFile resImages = residentDTO.getResImages();
            Date updateDate = new Date();
            String resFileName = updateDate.getTime() + "_" + resImages.getOriginalFilename();

            try (InputStream inputStream = resImages.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadImages + resFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            resident.setResImageAddress(resFileName);
        }

        resident.setResName(residentDTO.getResName()); // 이름
        resident.setResGender(residentDTO.getResGender()); // 성별
        resident.setResBirth(residentDTO.getResBirth());
        ; // 생년월일
        resident.setResPhone(residentDTO.getResPhone()); // 전화번호
        resident.setResGrade(residentDTO.getResGrade()); // 등급
        resident.setDementiaYn(residentDTO.isDementiaYn()); // 치매 유무
        resident.setFallYn(residentDTO.isFallYn()); // 낙상 위험
        resident.setBedsoreYn(residentDTO.isBedsoreYn()); // 욕창 위험
        resident.setPostureYn(residentDTO.isPostureYn()); // 자세변경
        resident.setResDisease(residentDTO.getResDisease()); // 주요질환
        resident.setResLocation(residentDTO.getResLocation()); // 생활실
        resident.setResEnterDate(residentDTO.getResEnterDate()); // 입소일
        resident.setResExitDate(residentDTO.getResExitDate()); // 퇴소일
        resident.setResAddress(residentDTO.getResAddress()); // 주소
        resident.setResSchoolGrade(residentDTO.getResSchoolGrade()); // 최종학력
        resident.setResLongTermCareNo(residentDTO.getResLongTermCareNo()); // 장기요양인정번호
        resident.setResCareGroup(residentDTO.getResCareGroup()); // 케어그룹
        resident.setResFoodType(residentDTO.getResFoodType()); // 식사종류
        resident.setResFunctionDis(residentDTO.getResFunctionDis()); // 기능장애

        return residentRepository.save(resident);
    }

    // 입소자 정보 삭제
    @Override
    public void deleteResident(Long redId) {
        try {
            Resident resident = residentRepository.findById(redId).get();
            String uploadImages = "public/images/";
            Path uploadImagesPath = Paths.get(uploadImages + resident.getResImageAddress());

            try {
                Files.delete(uploadImagesPath);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            residentRepository.delete(resident);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Resident> getAllResidents(ResidentDTO residentDTO) {
        List<Resident> residents = residentRepository.findAll();
        return residents;
    }

    @Override
    public Resident getResidentById(Long resId) {
        return residentRepository.findByResId(resId)
                .orElseThrow(() -> new RuntimeException("입소자 정보가 없습니다." + resId));
    }

    @Override
    public Guard createResidentGuard(GuardDTO guardDTO) {
        System.out.println("가능?" + guardDTO.getSsn());
        User user = userRepository.findBySsn(guardDTO.getSsn())
                .orElseThrow(() -> new RuntimeException("Guard not found with ssn: " + guardDTO.getSsn()));
        Long resId = guardDTO.getResId();
        Resident resident = residentRepository.findById(resId)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + resId));
        Guard guard = user.getGuard();
        guard.setResident(resident);

        return guardRepository.save(guard);
    }
}
