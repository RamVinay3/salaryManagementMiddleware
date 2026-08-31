package com.vinay.salaryManagement.Exception;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Long employeeId) {
        super("Employee not found with id: " + employeeId);
    }

    public EmployeeNotFoundException(String employeeCode) {
        super("Employee not found with code: " + employeeCode);
    }
}
