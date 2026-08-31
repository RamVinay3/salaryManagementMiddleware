package com.vinay.salaryManagement.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryUpdateRequest {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    private String currency;

    @NotNull
    private LocalDate effectiveDate;
}