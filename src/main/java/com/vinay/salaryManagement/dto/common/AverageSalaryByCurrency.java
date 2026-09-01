package com.vinay.salaryManagement.dto.common;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AverageSalaryByCurrency {

    private String currency;
    private Double averageSalary;
}