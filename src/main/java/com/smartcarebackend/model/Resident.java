package com.smartcarebackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resId;

    @Column(name = "res_name")
    private String resName; // 이름

    @Column(name = "res_genter", length = 10)
    private String resGenter; // 성별

    @Column(name = "res_birth")
    private LocalDate resBirth; // 생년월일

    @Column(name = "res_phone", length = 15)
    private String resPhone; // 전화번호

    @Column(name = "res_grade", length = 50)
    private String resGrade; // 등급

    @Column(name = "dementia_yn")
    private boolean dementiaYn; // 치매 유무

    @Column(name = "fall_yn")
    private boolean fallYn; // 낙상 위험

    @Column(name = "bedsore_yn")
    private boolean bedsoreYn; // 욕창 위험

    @Column(name = "posture_yn")
    private boolean postureYn; // 자세변경

    @Column(name = "res_disease")
    private String resDisease; // 주요질환

    @Column(name = "res_location")
    private String resLocation; // 생활실

    @Column(name = "res_enter_date")
    private LocalDate resEnterDate; // 입소일

    @Column(name = "res_exit_date")
    private LocalDate resExitDate; // 퇴소일

    @Column(name = "res_address", length = 255)
    private String resAddress; // 주소

    @Column(name = "system_res_code", length = 50)
    private String systemResCode; // 요양시스템 입소자 코드

    @Column(name = "res_school_grade", length = 50)
    private String resSchoolGrade; // 최종학력

    @Column(name = "res_long_term_care_no", length = 50)
    private String resLongTermCareNo; // 장기요양인정번호

    @Column(name = "res_care_group", length = 50)
    private String resCareGroup; // 케어그룹

    @Column(name = "res_food_type", length = 50)
    private String resFoodType; // 식사종류

    @Column(name = "res_function_dis")
    private String resFunctionDis; // 기능장애

    @Column(name = "res_image_address")
    private String resImageAddress; // 입소자 사진

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id", referencedColumnName = "giverId")
    private Giver giver;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Guard> guards;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cist> cists;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> acts;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meal> meals;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visits;
}
