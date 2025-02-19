package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.ResidentDTO;
import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.ResidentRepository;
import com.smartcarebackend.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
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
    public ResidentServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }


    // 입소자 정보 등록
    @Override
    public Resident createResident(Long giverId, ResidentDTO residentDTO) {

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

        // Giver 객체를 giverId로 찾아서 할당
        Giver giver = giverRepository.findById(giverId)
                .orElseThrow(() -> new IllegalArgumentException("Giver not found for ID: " + residentDTO.getGiverId()));


        Resident resident = new Resident();
        resident.setGiver(giver); // 요양보호사 ID
        resident.setResName(residentDTO.getResName()); // 이름
        resident.setResGender(residentDTO.getResGender()); // 성별
        resident.setResBirth(residentDTO.getResBirth());; // 생년월일
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
        resident.setSystemResCode(residentDTO.getSystemResCode()); // 요양시스템 입소자 코드
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
        if (residentDTO.getResImages() != null){
            String uploadImages = "public/images";
            Path oldImagePath = Paths.get(uploadImages + resident.getResImageAddress());
            try {
                Files.delete(oldImagePath);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            MultipartFile resImages = residentDTO.getResImages();
            Date updateDate = new Date();
            String resFileName = updateDate.getTime() + "_" + resImages.getOriginalFilename();

            try(InputStream inputStream = resImages.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadImages + resFileName), StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            resident.setResImageAddress(resFileName);
        }
        resident.setGiver(resident.getGiver()); // 요양보호사 ID

        if(residentDTO.getResName() != null){
            resident.setResName(residentDTO.getResName()); // 이름
        }

        if(residentDTO.getResGender() != null){
            resident.setResGender(residentDTO.getResGender());
        }

        if(residentDTO.getResBirth() != null){
            resident.setResBirth(residentDTO.getResBirth());
        }

        if(residentDTO.getResPhone() != null){
            resident.setResPhone(residentDTO.getResPhone());
        }

        if(residentDTO.getResGrade() != null){
            resident.setResGrade(residentDTO.getResGrade());
        }

//        resident.setResGender(residentDTO.getResGender()); // 성별
//        resident.setResBirth(residentDTO.getResBirth());; // 생년월일
//        resident.setResPhone(residentDTO.getResPhone()); // 전화번호
//        resident.setResGrade(residentDTO.getResGrade()); // 등급

        resident.setDementiaYn(residentDTO.isDementiaYn()); // 치매 유무
        resident.setFallYn(residentDTO.isFallYn()); // 낙상 위험
        resident.setBedsoreYn(residentDTO.isBedsoreYn()); // 욕창 위험
        resident.setPostureYn(residentDTO.isPostureYn()); // 자세변경
        resident.setResDisease(residentDTO.getResDisease()); // 주요질환
        resident.setResLocation(residentDTO.getResLocation()); // 생활실
        resident.setResEnterDate(residentDTO.getResEnterDate()); // 입소일
        resident.setResExitDate(residentDTO.getResExitDate()); // 퇴소일
        resident.setResAddress(residentDTO.getResAddress()); // 주소
        resident.setSystemResCode(residentDTO.getSystemResCode()); // 요양시스템 입소자 코드
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
        System.out.println("Error: " + e.getMessage());}
    }

    @Override
    public List<Resident> getAllResidents(ResidentDTO residentDTO) {
        List<Resident> residents = residentRepository.findAll();
        return residents;
    }

    @Override
    public Resident getResidentById(Long resId) {
        return residentRepository.findByResId(resId)
                .orElseThrow(()->new RuntimeException("입소자 정보가 없습니다." + resId));
    }
}
