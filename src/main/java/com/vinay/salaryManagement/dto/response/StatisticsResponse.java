package com.vinay.salaryManagement.dto.response;
import com.vinay.salaryManagement.dto.common.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsResponse {

    private Long totalEmployees;

    private List<EmployeeCountByCountry> employeesByCountry;

    private List<EmployeeCountByDepartment> employeesByDepartment;

    private List<AverageSalaryByCurrency> averageSalaryByCurrency;
    private List<SalaryStatisticsByCountry> salaryStatisticsByCountry;
}
