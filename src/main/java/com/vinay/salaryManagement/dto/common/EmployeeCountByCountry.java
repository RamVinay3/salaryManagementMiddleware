package com.vinay.salaryManagement.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCountByCountry {

    private String country;
    private Long count;
}