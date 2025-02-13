package com.smartcarebackend.repositories;

import com.smartcarebackend.model.AppRole;
import com.smartcarebackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(AppRole roleName);
}
