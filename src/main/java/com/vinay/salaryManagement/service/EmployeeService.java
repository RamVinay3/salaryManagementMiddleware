package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found: " + id));
    }

    public Employee getEmployeeByCode(String employeeCode) {
        return employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found: " + employeeCode
                        ));
    }

    public Page<Employee> getEmployeesByDepartment(
            Long departmentId,
            Pageable pageable
    ) {
        return employeeRepository.findByDepartmentId(
                departmentId,
                pageable
        );
    }

    public Page<Employee> getEmployeesByCountry(
            String country,
            Pageable pageable
    ) {
        return employeeRepository.findByCountry(
                country,
                pageable
        );
    }

    public Page<Employee> searchEmployees(
            String lastName,
            Pageable pageable
    ) {
        return employeeRepository.findByLastNameContainingIgnoreCase(
                lastName,
                pageable
        );
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}