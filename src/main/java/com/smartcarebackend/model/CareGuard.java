package com.smartcarebackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CareGuard {
    @Id
    @Column(name = "GUARD_ID", length = 10, nullable = false)
    private String guardId;  // 보호자 ID (Primary Key)

//    @ManyToOne
//    @JoinColumn(name = "RES_ID", nullable = false)  // 입소자 ID (Foreign Key)
//    private Resident resident;  // 입소자와의 관계 (RES_ID)

    @Column(name = "GUARD_LOGIN", length = 50, nullable = false, unique = true)
    private String guardLogin;  // 보호자 로그인 아이디

    @Column(name = "GUARD_PWD", length = 255, nullable = false)
    private String guardPwd;  // 보호자 비밀번호 (암호화 저장 권장)

    @Column(name = "GUARD_NAME", length = 30, nullable = false)
    private String guardName;  // 보호자 이름

    @Column(name = "RELATION", length = 12)
    private String relation;  // 환자와의 관계 (예: 부모, 자녀, 배우자 등)

    @Column(name = "GUARD_PHONE", length = 15)
    private String guardPhone;  // 보호자 휴대폰 번호

    @Column(name = "GUARD_SSN", length = 13, nullable = false, unique = true)
    private String guardSsn;  // 보호자 주민등록번호 (중복 체크)

    @Column(name = "GUARD_TOS", nullable = false)
    private boolean guardTos;  // 이용약관 동의 여부



}
