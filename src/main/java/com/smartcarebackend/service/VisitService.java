package com.smartcarebackend.service;

import com.smartcarebackend.model.Visit;

import java.util.List;

public interface VisitService {

    List<Visit> getAllVisits();

    List<Visit> getVisitsByGuardId(Long guardId);
}
