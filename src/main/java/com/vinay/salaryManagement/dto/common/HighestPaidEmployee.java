package com.vinay.salaryManagement.dto.common;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HighestPaidEmployee {

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private BigDecimal salary;

    private String currency;
}