package com.vinay.salaryManagement.mapper;

import com.vinay.salaryManagement.dto.response.SalaryResponse;
import com.vinay.salaryManagement.dto.request.SalaryUpdateRequest;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.entity.Salary;
import org.springframework.stereotype.Component;

@Component
public class SalaryMapper {
    public Salary toEntity(
            SalaryUpdateRequest request,
            Employee employee
    ) {
        return Salary.builder()
                .employee(employee)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .effectiveDate(request.getEffectiveDate())
                .build();
    }
    public SalaryResponse toResponse(Salary salary) {
        return SalaryResponse.builder()
                .id(salary.getId())
                .employeeId(salary.getEmployee().getId())
                .amount(salary.getAmount())
                .currency(salary.getCurrency())
                .effectiveDate(salary.getEffectiveDate())
                .build();
    }
}