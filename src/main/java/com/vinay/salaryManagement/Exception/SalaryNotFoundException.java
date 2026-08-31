package com.vinay.salaryManagement.Exception;

public class SalaryNotFoundException extends  RuntimeException{
    public SalaryNotFoundException(Long employeeId) {
        super("Salary not found for employee with id: " + employeeId);
    }
}
