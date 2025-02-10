package com.smartcarebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUARD_I D", length = 10, nullable = false)
    private Long guardId;  // 보호자 ID (Primary Key)

//    @ManyToOne
//    @JoinColumn(name = "RES_ID", nullable = false)  // 입소자 ID (Foreign Key)
//    private Resident resident;  // 입소자와의 관계 (RES_ID)

    @Column(name = "GUARD_LOGIN", length = 50, nullable = false, unique = true)
    private String guardLogin;  // 보호자 로그인 아이디

    @Column(name = "GUARD_PWD", length = 255, nullable = false)
    private String guardPwd;  // 보호자 비밀번호

    @Column(name = "GUARD_NAME", length = 30, nullable = false)
    private String guardName;  // 보호자 이름

    @Column(name = "RELATION", length = 12)
    private String relation;  // 환자와의 관계 (예: 부모, 자녀, 배우자 등)

    @Column(name = "GUARD_PHONE", length = 15)
    private String guardPhone;  // 보호자 휴대폰 번호

    @Column(name = "GUARD_SSN", length = 13, unique = true)
    private String guardSsn;  // 보호자 주민등록번호 (중복 체크)

    @Column(name = "GUARD_TOS", nullable = false)
    private boolean guardTos;  // 이용약관 동의 여부

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonBackReference
    @ToString.Exclude
    private Role role;

}
