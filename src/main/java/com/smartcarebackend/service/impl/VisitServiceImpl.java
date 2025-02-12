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
    public List<Visit> getVisitsByGuardId(Long userId) {
        List<Visit> VisitByUser = visitRepository.findByUserId(userId);
        if(VisitByUser.isEmpty()||VisitByUser==null){
            throw  new RuntimeException("No User Found");
        }
        return VisitByUser;
    }

    @Override
    public Visit createVisit(Long userId, Long resId) {
        return null;
    }

}
