package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuardRepository extends JpaRepository<Guard, Long> {
    Guard findByUser(User user);

    List<Guard> findByResident(Resident resident); // Resident 해당하는 모든 보호자 조회
}
