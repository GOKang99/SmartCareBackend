package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guards")
public class Guard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guardId;

    @Column(length = 12)
    private String relation;  //환자와의 관계 (예: 부모, 자녀, 배우자 등)

    @Column(length = 15)
    private String phone;  //휴대폰 번호

    @Column(length = 13, unique = true)
    private String ssn;  //주민등록번호 (중복 체크)

    @Column(nullable = false)
    private boolean agree;  //약관동의 여부

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id", referencedColumnName = "resId",nullable = true)
    private Resident resident;

    @OneToMany(mappedBy = "guard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visits;
}
