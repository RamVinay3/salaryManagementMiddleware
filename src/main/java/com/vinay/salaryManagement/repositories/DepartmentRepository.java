package com.vinay.salaryManagement.repositories;

import com.vinay.salaryManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    Optional<Department> findByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
