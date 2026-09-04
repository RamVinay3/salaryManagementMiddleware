package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.exception.EmployeeNotFoundException;
import com.vinay.salaryManagement.exception.SalaryNotFoundException;
import com.vinay.salaryManagement.dto.request.SalaryUpdateRequest;
import com.vinay.salaryManagement.dto.response.SalaryResponse;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.entity.Salary;
import com.vinay.salaryManagement.mapper.SalaryMapper;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import com.vinay.salaryManagement.repositories.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final SalaryMapper salaryMapper;
    private final EmployeeRepository employeeRepository;

    public SalaryResponse createSalary(
            Long employeeId,
            SalaryUpdateRequest request
    ) {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                 employeeId
                        )
                );

        Salary salary = salaryMapper.toEntity(
                request,
                employee
        );

        Salary savedSalary =
                salaryRepository.save(salary);

        return salaryMapper.toResponse(savedSalary);
    }
    public SalaryResponse getCurrentSalary(Long employeeId) {

        boolean employeeFound = employeeRepository.existsById(employeeId);

        if(!employeeFound) {
            throw new EmployeeNotFoundException(employeeId);
        }
        Salary salary= salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(employeeId)
                .orElseThrow(() ->
                        new SalaryNotFoundException( employeeId ));

        return salaryMapper.toResponse(salary);
    }

    public List<SalaryResponse> getSalaryHistory(Long employeeId) {

        boolean employeeFound = employeeRepository.existsById(employeeId);

        if(!employeeFound) {
            throw new EmployeeNotFoundException(employeeId);
        }
        List<Salary> salary= salaryRepository.findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new SalaryNotFoundException( employeeId ));

        return salary.stream().map(salaryMapper::toResponse).toList();
    }


}