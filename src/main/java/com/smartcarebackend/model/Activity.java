package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Activity {

    @Id
    @Column(name = "ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId; //활동 아이디

    @CreationTimestamp
    @Column(name = "ACTIVITY_LAST_DT", nullable = false)
    private LocalDateTime activityLastDt; //최근 측정일

    @Column(name = "ACTIVITY_SKELETAL_MASS", nullable = false)
    private Double skeletalMass; //골격근량

    @Column(name = "ACTIVITY_WEIGHT", nullable = false)
    private Double weight; //체중

    @Column(name = "ACTIVITY_HEIGHT", nullable = false)
    private Double height; //신장

    @Column(name = "ACTIVITY_FAT_MASS", nullable = false)
    private Double fatMass; //체지방량

    @Column(name = "ACTIVITY_FAT_PERCENT", nullable = false)
    private Double fatPercent; //체지방률
}
