package com.vinay.salaryManagement.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCountByDepartment {

    private String department;
    private Long count;
}