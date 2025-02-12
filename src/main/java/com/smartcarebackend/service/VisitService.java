package com.smartcarebackend.service;

import com.smartcarebackend.model.Visit;

import java.util.List;

public interface VisitService {

    //모든 방문자를 가지고 오는 메소드
    List<Visit> getAllVisits();

    //로그인 후 개인 예약 내역을 확인하는 메소드
    List<Visit> getVisitsByGuardId(Long userId);

    //유저에 대한 예약 생성
    Visit createVisit(Long userId, Long resId  );
}
