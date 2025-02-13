package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByResId(Long resId);


}
