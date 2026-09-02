package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.dto.response.StatisticsResponse;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import com.vinay.salaryManagement.repositories.SalaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryRepository salaryRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void shouldReturnStatistics() {

        // ARRANGE

        when(employeeRepository.countEmployees())
                .thenReturn(100L);

        List<Object[]> countryRows = List.of(
                new Object[]{"IN", 60L},
                new Object[]{"US", 30L},
                new Object[]{"UK", 10L}
        );

        when(employeeRepository.countEmployeesByCountry())
                .thenReturn(countryRows);

        List<Object[]> departmentRows = List.of(
                new Object[]{"Engineering", 50L},
                new Object[]{"Finance", 30L},
                new Object[]{"HR", 20L}
        );

        when(employeeRepository.countEmployeesByDepartment())
                .thenReturn(departmentRows);

        List<Object[]> salaryRows = List.of(
                new Object[]{"INR", 1800000.0},
                new Object[]{"USD", 120000.0}
        );

        when(salaryRepository.findAverageCurrentSalaryByCurrency())
                .thenReturn(salaryRows);


        // ACT

        StatisticsResponse result =
                statisticsService.getStatistics();


        // ASSERT

        assertEquals(100L, result.getTotalEmployees());

        assertEquals(
                3,
                result.getEmployeesByCountry().size()
        );

        assertEquals(
                "IN",
                result.getEmployeesByCountry()
                        .get(0)
                        .getCountry()
        );

        assertEquals(
                60L,
                result.getEmployeesByCountry()
                        .get(0)
                        .getCount()
        );

        assertEquals(
                3,
                result.getEmployeesByDepartment().size()
        );

        assertEquals(
                "Engineering",
                result.getEmployeesByDepartment()
                        .get(0)
                        .getDepartment()
        );

        assertEquals(
                2,
                result.getAverageSalaryByCurrency().size()
        );

        assertEquals(
                "INR",
                result.getAverageSalaryByCurrency()
                        .get(0)
                        .getCurrency()
        );

        assertEquals(
                1800000.0,
                result.getAverageSalaryByCurrency()
                        .get(0)
                        .getAverageSalary()
        );
    }

}
