package com.smartcarebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {
    private Long medId;
    private String meaDt;
    private String fundDis;
    private String breTp;
    private String breQty;

    private String lunTp;
    private String lunQty;

    private String dinTp;
    private String dinQty;

    private String morSnackQty;

    private String aftSnackQty;

    private String remark;
    private String resName; // 환자이름
    private Long resMealId; // Resident ID
    private Long giver;


}