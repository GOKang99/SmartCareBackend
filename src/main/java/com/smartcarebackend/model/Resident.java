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
    private Long redId;

    @Column(name = "giver_id")
    private Long giverId;

    @Column(name = "res_genter", length = 10)
    private String resGenter;

    @Column(name = "res_birth")
    private LocalDate resBirth;

    @Column(name = "res_phone", length = 15)
    private String resPhone;

    @Column(name = "res_grade", length = 50)
    private String resGrade;

    @Column(name = "dementia_yn")
    private boolean dementiaYn;

    @Column(name = "fall_yn")
    private boolean fallYn;

    @Column(name = "bedsore_yn")
    private boolean bedsoreYn;

    @Column(name = "posture_yn")
    private boolean postureYn;

    @Column(name = "res_disease")
    private String resDisease;

    @Column(name = "res_location")
    private String resLocation;

    @Column(name = "res_enter_date")
    private LocalDate resEnterDate;

    @Column(name = "res_exit_date")
    private LocalDate resExitDate;

    @Column(name = "res_address", length = 255)
    private String resAddress;

    @Column(name = "system_res_code", length = 50)
    private String systemResCode;

    @Column(name = "res_school_grade", length = 50)
    private String resSchoolGrade;

    @Column(name = "res_long_term_care_no", length = 50)
    private String resLongTermCareNo;

    @Column(name = "res_care_group", length = 50)
    private String resCareGroup;

    @Column(name = "res_food_type", length = 50)
    private String resFoodType;

    @Column(name = "res_function_dis")
    private String resFunctionDis;

    @Column(name = "res_image_address")
    private String resImageAddress;

    @ManyToMany(mappedBy = "residents")
    private List<User> users;
}
