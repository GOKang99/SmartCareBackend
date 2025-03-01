package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Meal {
    @Id
    @Column(name = "MED_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medId; //식사일지 아이디

    @Column(name = "MEA_DT")
    private String meaDt;  // 일자(년월일시분초)

    @Column(name = "FUND_DIS", length = 50)
    private String fundDis; //기능장애 (ex:경증,중증)

    @Column(name = "BRE_TP", length = 30)
    private String breTp; //아침식사유형

    @Column(name = "BRE_QTY", length = 20)
    private String breQty;  // 아침식사량 (예: 1인분, 2인분)

    @Column(name = "BRE_TIME")
    private LocalTime breTime;  // 아침식사시간 (예: 08:00)

    @Column(name = "LUN_TP", length = 30)
    private String lunTp;  // 점심식사유형 (예: "영양식", "일반식")

    @Column(name = "LUN_QTY",length = 20 )
    private String lunQty;  // 점심식사량 (예: 1인분, 2인분)

    @Column(name = "LUN_TIME")
    private LocalTime lunTime;  // 점심식사시간 (예: 12:00)

    @Column(name = "DIN_TP", length = 30)
    private String dinTp;  // 저녁식사유형 (예: "영양식", "일반식")

    @Column(name = "DIN_QTY", length = 20 )
    private String dinQty;  // 저녁식사량 (예: 1인분, 2인분)

    @Column(name = "DIN_TIME")
    private LocalTime dinTime;  // 저녁식사시간 (예: 18:00)

    @Column(name = "MOR_SNACK_QTY", length = 20)
    private String morSnackQty;  // 오전간식량 (예: 1인분, 2인분)

    @Column(name = "MOR_SNACK_TIME")
    private LocalTime morSnackTime;  // 오전간식시간 (예: 10:00)

    @Column(name = "AFT_SNACK_QTY", length = 20 )
    private String aftSnackQty;  // 오후간식량 (예: 1인분, 2인분)

    @Column(name = "AFT_SNACK_TIME")
    private LocalTime aftSnackTime;  // 오후간식시간 (예: 15:00)

    @Column(name = "REMARK", length = 255)
    private String remark;  // 비고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id", referencedColumnName = "resId")
    private Resident resident;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id", referencedColumnName = "giverId")
    private Giver giver;

    //@Column(name = "DIETARY_CURE", length = 50)
    //private String dietaryCure; // 치료식이
    //제이스이그널 사용하면 해결되됨
}
