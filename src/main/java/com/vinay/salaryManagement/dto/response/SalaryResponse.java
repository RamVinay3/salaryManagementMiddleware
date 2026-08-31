package com.vinay.salaryManagement.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryResponse {

    private Long id;
    private Long employeeId;
    private BigDecimal amount;
    private String currency;
    private LocalDate effectiveDate;
}