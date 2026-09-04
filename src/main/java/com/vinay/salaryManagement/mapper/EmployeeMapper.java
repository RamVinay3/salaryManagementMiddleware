package com.vinay.salaryManagement.mapper;

import com.vinay.salaryManagement.dto.common.PageResponse;
import com.vinay.salaryManagement.dto.request.EmployeeCreateRequest;
import com.vinay.salaryManagement.dto.request.EmployeeUpdateRequest;
import com.vinay.salaryManagement.dto.response.EmployeeResponse;
import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(
            EmployeeCreateRequest request,
            Department department
    ) {
        return Employee.builder()
                .employeeCode(request.getEmployeeCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .country(request.getCountry())
                .department(department)
                .jobTitle(request.getJobTitle())
                .hireDate(request.getHireDate())
                .build();
    }

    public void updateEntity(
            Employee employee,
            EmployeeUpdateRequest request,
            Department department
    ) {
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setCountry(request.getCountry());
        employee.setDepartment(department);
        employee.setJobTitle(request.getJobTitle());
        employee.setHireDate(request.getHireDate());
    }

    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .country(employee.getCountry())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getName())
                .jobTitle(employee.getJobTitle())
                .hireDate(employee.getHireDate())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }


}