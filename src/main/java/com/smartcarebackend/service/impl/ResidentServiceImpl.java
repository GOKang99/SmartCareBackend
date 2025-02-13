package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.repositories.ResidentRepository;
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
    public ResidentServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }


    @Override
    public Resident createResident(ResidentDTO residentDTO) {
        MultipartFile resImages = residentDTO.getResImages();
        Date createDate = new Date();
        String resFileName = createDate.getTime() + "_" + resImages.getOriginalFilename();

        try {
            String uploadImages = "public/images";
            Path uploadImagesPath = Paths.get(uploadImages);

            if (!Files.exists(uploadImagesPath)) {
                Files.createDirectory(uploadImagesPath);
            }
            try(InputStream inputStream = resImages.getInputStream()) {
                Files.copy(inputStream,Paths.get(uploadImages + resFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        Resident resident = new Resident();

        resident.setResName(residentDTO.getResName());  // 이름 설정
        resident.setResGender(residentDTO.getResGender()); // 성별 설정
        resident.setResBirth(residentDTO.getResBirth());  // 생년월일 설정
        resident.setResPhone(residentDTO.getResPhone());  // 전화번호 설정
        resident.setResGrade(residentDTO.getResGrade());  // 등급 설정
        resident.setDementiaYn(residentDTO.isDementiaYn());  // 치매 유무 설정
        resident.setFallYn(residentDTO.isFallYn());  // 낙상 위험 설정
        resident.setBedsoreYn(residentDTO.isBedsoreYn());  // 욕창 위험 설정
        resident.setPostureYn(residentDTO.isPostureYn());  // 자세변경 설정
        resident.setResDisease(residentDTO.getResDisease());  // 주요질환 설정
        resident.setResLocation(residentDTO.getResLocation());  // 생활실 설정
        resident.setResEnterDate(residentDTO.getResEnterDate());  // 입소일 설정
        resident.setResExitDate(residentDTO.getResExitDate());  // 퇴소일 설정
        resident.setResAddress(residentDTO.getResAddress());  // 주소 설정
        resident.setSystemResCode(residentDTO.getSystemResCode());  // 요양시스템 입소자 코드 설정
        resident.setResSchoolGrade(residentDTO.getResSchoolGrade());  // 최종학력 설정
        resident.setResLongTermCareNo(residentDTO.getResLongTermCareNo());  // 장기요양인정번호 설정
        resident.setResCareGroup(residentDTO.getResCareGroup());  // 케어그룹 설정
        resident.setResFoodType(residentDTO.getResFoodType());  // 식사종류 설정
        resident.setResFunctionDis(residentDTO.getResFunctionDis());  // 기능장애 설정
        resident.setResImageAddress(resFileName); // 이미지 주소

        return residentRepository.save(resident);
    }

    @Override
    public Resident updateResident(Long redId, ResidentDTO residentDTO) {
        return null;
    }

    @Override
    public void deleteResident(Long redId) {

    }

    @Override
    public List<Resident> getAllResidents() {
        return List.of();
    }
}
