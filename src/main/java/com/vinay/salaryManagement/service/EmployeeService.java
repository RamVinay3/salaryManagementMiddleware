package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.dto.common.PageResponse;
import com.vinay.salaryManagement.exception.DepartmentNotFoundException;
import com.vinay.salaryManagement.exception.EmployeeNotFoundException;
import com.vinay.salaryManagement.dto.request.EmployeeCreateRequest;
import com.vinay.salaryManagement.dto.request.EmployeeUpdateRequest;
import com.vinay.salaryManagement.dto.response.EmployeeResponse;
import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.mapper.EmployeeMapper;
import com.vinay.salaryManagement.mapper.PageMapper;
import com.vinay.salaryManagement.repositories.DepartmentRepository;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;
    private final PageMapper pageMapper;

    public PageResponse<EmployeeResponse> getEmployees(
            Long departmentId,
            String country,
            String search,
            Pageable pageable
    ) {


        System.out.println("departmentId= "+departmentId+" country= "+country+" search= "+search);
        Page<EmployeeResponse> employees = employeeRepository.searchEmployees(
                        departmentId,
                        country,
                        search,
                        pageable
                ).map(employeeMapper::toResponse);

        return pageMapper.toPageResponse(employees);

    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(id));

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

    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        employeeRepository.deleteById(id);
    }

    public EmployeeResponse saveEmployee(EmployeeCreateRequest request) {

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new DepartmentNotFoundException(request.getDepartmentId())
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
                        new DepartmentNotFoundException(request.getDepartmentId())
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
                        new EmployeeNotFoundException(id)
                );

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                               request.getDepartmentId()
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