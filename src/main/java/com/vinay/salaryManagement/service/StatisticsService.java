package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.dto.request.*;
import com.vinay.salaryManagement.dto.common.AverageSalaryByCurrency;
import com.vinay.salaryManagement.dto.common.EmployeeCountByCountry;
import com.vinay.salaryManagement.dto.common.EmployeeCountByDepartment;
import com.vinay.salaryManagement.dto.response.StatisticsResponse;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import com.vinay.salaryManagement.repositories.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;

    public StatisticsResponse getStatistics() {

        Long totalEmployees =
                employeeRepository.countEmployees();

        List<EmployeeCountByCountry> employeesByCountry =
                employeeRepository.countEmployeesByCountry()
                        .stream()
                        .map(row -> EmployeeCountByCountry.builder()
                                .country((String) row[0])
                                .count((Long) row[1])
                                .build())
                        .toList();

        List<EmployeeCountByDepartment> employeesByDepartment =
                employeeRepository.countEmployeesByDepartment()
                        .stream()
                        .map(row -> EmployeeCountByDepartment.builder()
                                .department((String) row[0])
                                .count((Long) row[1])
                                .build())
                        .toList();

        List<AverageSalaryByCurrency> averageSalaryByCurrency =
                salaryRepository.findAverageCurrentSalaryByCurrency()
                        .stream()
                        .map(row -> AverageSalaryByCurrency.builder()
                                .currency((String) row[0])
                                .averageSalary((Double) row[1])
                                .build())
                        .toList();

        return StatisticsResponse.builder()
                .totalEmployees(totalEmployees)
                .employeesByCountry(employeesByCountry)
                .employeesByDepartment(employeesByDepartment)
                .averageSalaryByCurrency(averageSalaryByCurrency)
                .build();
    }
}