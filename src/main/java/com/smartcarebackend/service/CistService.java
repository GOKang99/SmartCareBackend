package com.smartcarebackend.service;

import com.smartcarebackend.dto.CistDTO;

import java.util.List;

public interface CistService {
    CistDTO createCist(CistDTO cistDTO);
    CistDTO updateCist(Long id, CistDTO cistDTO);
    void deleteCist(Long id);
    List<CistDTO> getCistByResident(Long residentId);
}
