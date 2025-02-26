package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "composition")

public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comId;

    @Column(name = "COM_DT", nullable = false)
    private LocalDate comDate; // 일자

    @Column(name = "COM_HEIGHT", nullable = false)
    private Double comHeight; // 신장

    @Column(name = "COM_WEIGHT", nullable = false)
    private Double comWeight; // 체중

    @Column(name = "COM_SMM", nullable = false)
    private Double comSmm; // 골격근량

    @Column(name = "COM_BFM", nullable = false)
    private Double comBfm; // 체지방량

    @Column(name = "COM_PBF", nullable = false)
    private Double comPbf; // 체지방율

    @Column(name = "COM_BMI", nullable = false)
    private Double comBmi; // BMI

    @Column(name = "COM_FAT_LVL", nullable = false)
    private Integer comFatLvl; // 내장지방레벨

    //참조하기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id", referencedColumnName = "resId")
    private Resident resident;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id", referencedColumnName = "giverId")
    private Giver giver;
}
