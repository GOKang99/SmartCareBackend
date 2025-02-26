package com.smartcarebackend.repositories;

import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardRepository extends JpaRepository<Guard, Long> {
    Guard findByUser(User user);

    Optional<Guard> findBySsn(String ssn);
}
