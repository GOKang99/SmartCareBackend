package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, Long> {
    List<Composition> findAllByResident_ResId(Long resId);
}
