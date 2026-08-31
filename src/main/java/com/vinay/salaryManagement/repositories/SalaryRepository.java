package com.vinay.salaryManagement.repositories;

import com.vinay.salaryManagement.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary,Long> {
    Optional<Salary> findTopByEmployeeIdOrderByEffectiveDateDesc(
            Long employeeId
    );

}
