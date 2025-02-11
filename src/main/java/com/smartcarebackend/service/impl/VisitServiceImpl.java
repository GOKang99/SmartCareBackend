package com.smartcarebackend.service.impl;

import com.smartcarebackend.model.Visit;
import com.smartcarebackend.repositories.VisitRepository;
import com.smartcarebackend.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    VisitRepository visitRepository;

    @Override
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public List<Visit> getVisitsByGuardId(Long guardId) {
        List<Visit> VisitByGuard = visitRepository.findByGuardId(guardId);
        if(VisitByGuard.isEmpty()||VisitByGuard==null){
            throw  new RuntimeException("No Guard Found");
        }
        return VisitByGuard;
    }

    @Override
    public Visit createVisit(Long guardId, Long resId) {
        return null;
    }

}
