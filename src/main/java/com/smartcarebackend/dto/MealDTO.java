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
    private LocalTime breTime;
    private String lunTp;
    private String lunQty;
    private LocalTime lunTime;
    private String dinTp;
    private String dinQty;
    private LocalTime dinTime;
    private String morSnackQty;
    private LocalTime morSnackTime;
    private String aftSnackQty;
    private LocalTime aftSnackTime;
    private String remark;
    private Long resMealId; // Resident ID
    private Long giver;
}