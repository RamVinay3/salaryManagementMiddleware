package com.vinay.salaryManagement.Exception;

public class DepartmentNotFoundException extends RuntimeException{

    public DepartmentNotFoundException(Long departmentId) {
        super("Department not found with id: " + departmentId);
    }

    public DepartmentNotFoundException(String departmentName) {
        super("Department not found with name: " + departmentName);
    }
}
