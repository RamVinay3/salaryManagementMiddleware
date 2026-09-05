package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.dto.common.*;
import com.vinay.salaryManagement.dto.response.StatisticsResponse;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import com.vinay.salaryManagement.repositories.SalaryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        List<SalaryStatisticsByCountry> salaryStatisticsByCountry =
                buildSalaryStatisticsByCountry();

        return StatisticsResponse.builder()
                .totalEmployees(totalEmployees)
                .employeesByCountry(employeesByCountry)
                .employeesByDepartment(employeesByDepartment)
                .averageSalaryByCurrency(averageSalaryByCurrency)
                .salaryStatisticsByCountry(salaryStatisticsByCountry)
                .build();
    }

    private List<SalaryStatisticsByCountry> buildSalaryStatisticsByCountry() {

        List<Object[]> rows = salaryRepository.findCurrentSalaryDetails();

        Map<String, SalaryGroup> groups = new LinkedHashMap<>();

        for (Object[] row : rows) {

            Long employeeId = (Long) row[0];
            String employeeCode = (String) row[1];
            String firstName = (String) row[2];
            String lastName = (String) row[3];
            String country = (String) row[4];
            String currency = (String) row[5];
            BigDecimal amount = (BigDecimal) row[6];

            String key = country + "|" + currency;

            SalaryGroup group = groups.computeIfAbsent(
                    key,
                    ignored -> new SalaryGroup(country, currency)
            );

            group.add(
                    employeeId,
                    employeeCode,
                    firstName,
                    lastName,
                    amount
            );
        }

        return groups.values()
                .stream()
                .map(SalaryGroup::toResponse)
                .toList();
    }

    private static class SalaryGroup {

        private final String country;
        private final String currency;

        private long employeeCount;

        private BigDecimal minimumSalary;
        private BigDecimal maximumSalary;
        private BigDecimal salaryTotal;

        private final List<HighestPaidEmployee> highestPaidEmployees =
                new ArrayList<>();

        private SalaryGroup(String country, String currency) {
            this.country = country;
            this.currency = currency;
        }

        private void add(
                Long employeeId,
                String employeeCode,
                String firstName,
                String lastName,
                BigDecimal amount
        ) {

            employeeCount++;

            salaryTotal = salaryTotal == null
                    ? amount
                    : salaryTotal.add(amount);

            minimumSalary = minimumSalary == null
                    ? amount
                    : minimumSalary.min(amount);

            if (maximumSalary == null || amount.compareTo(maximumSalary) > 0) {

                maximumSalary = amount;

                highestPaidEmployees.clear();

                highestPaidEmployees.add(
                        createHighestPaidEmployee(
                                employeeId,
                                employeeCode,
                                firstName,
                                lastName,
                                amount
                        )
                );

            } else if (amount.compareTo(maximumSalary) == 0) {

                highestPaidEmployees.add(
                        createHighestPaidEmployee(
                                employeeId,
                                employeeCode,
                                firstName,
                                lastName,
                                amount
                        )
                );
            }
        }

        private HighestPaidEmployee createHighestPaidEmployee(
                Long employeeId,
                String employeeCode,
                String firstName,
                String lastName,
                BigDecimal amount
        ) {

            return HighestPaidEmployee.builder()
                    .employeeId(employeeId)
                    .employeeCode(employeeCode)
                    .employeeName(firstName + " " + lastName)
                    .salary(amount)
                    .currency(currency)
                    .build();
        }

        private SalaryStatisticsByCountry toResponse() {

            BigDecimal averageSalary =
                    salaryTotal.divide(
                            BigDecimal.valueOf(employeeCount),
                            2,
                            java.math.RoundingMode.HALF_UP
                    );

            return SalaryStatisticsByCountry.builder()
                    .country(country)
                    .currency(currency)
                    .employeeCount(employeeCount)
                    .minimumSalary(minimumSalary)
                    .averageSalary(averageSalary)
                    .maximumSalary(maximumSalary)
                    .highestPaidEmployeeCount(
                            (long) highestPaidEmployees.size()
                    )
                    .highestPaidEmployees(
                            List.copyOf(highestPaidEmployees)
                    )
                    .build();
        }
    }
}