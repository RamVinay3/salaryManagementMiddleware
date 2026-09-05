package com.vinay.salaryManagement.dto.common;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStatisticsByCountry {

    private String country;

    private String currency;

    private Long employeeCount;

    private BigDecimal minimumSalary;

    private BigDecimal averageSalary;

    private BigDecimal maximumSalary;

    private Long highestPaidEmployeeCount;

    private List<HighestPaidEmployee> highestPaidEmployees;
}