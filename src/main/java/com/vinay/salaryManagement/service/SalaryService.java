package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.entity.Salary;
import com.vinay.salaryManagement.repositories.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;

    public Salary getCurrentSalary(Long employeeId) {
        return salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(employeeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Salary not found for employee: " + employeeId
                        ));
    }

    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }
}