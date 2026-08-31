package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.dto.request.EmployeeCreateRequest;
import com.vinay.salaryManagement.dto.request.EmployeeUpdateRequest;
import com.vinay.salaryManagement.dto.response.EmployeeResponse;
import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.mapper.EmployeeMapper;
import com.vinay.salaryManagement.repositories.DepartmentRepository;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;

    public Page<EmployeeResponse> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toResponse);
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found: " + id));

        return employeeMapper.toResponse(employee);
    }

    public EmployeeResponse getEmployeeByCode(String employeeCode) {
        Employee employee= employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found: " + employeeCode
                        ));

        return employeeMapper.toResponse(employee);
    }

    public Page<EmployeeResponse> getEmployeesByDepartment(
            Long departmentId,
            Pageable pageable
    ) {
        return employeeRepository.findByDepartmentId(
                departmentId,
                pageable
        ).map(employeeMapper::toResponse);
    }

    public Page<EmployeeResponse> getEmployeesByCountry(
            String country,
            Pageable pageable
    ) {
        return employeeRepository.findByCountry(
                country,
                pageable
        ).map(employeeMapper::toResponse);
    }

    public Page<EmployeeResponse> searchEmployees(
            String lastName,
            Pageable pageable
    ) {
        return employeeRepository.findByLastNameContainingIgnoreCase(
                lastName,
                pageable
        ).map(employeeMapper::toResponse);
    }

    public EmployeeResponse saveEmployee(EmployeeCreateRequest request) {

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: "
                                        + request.getDepartmentId()
                        )
                );
        Employee employee1= employeeRepository.save(employeeMapper.toEntity(request,department));
        return employeeMapper.toResponse(employee1);
    }
    public EmployeeResponse createEmployee(
            EmployeeCreateRequest request
    ) {
        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: "
                                        + request.getDepartmentId()
                        )
                );

        Employee employee = employeeMapper.toEntity(
                request,
                department
        );

        Employee savedEmployee =
                employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeUpdateRequest request
    ) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found: " + id
                        )
                );

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: "
                                        + request.getDepartmentId()
                        )
                );

        employeeMapper.updateEntity(
                employee,
                request,
                department
        );

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return employeeMapper.toResponse(updatedEmployee);
    }
}