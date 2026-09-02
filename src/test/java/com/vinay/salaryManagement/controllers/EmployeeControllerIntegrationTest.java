package com.vinay.salaryManagement.controllers;

import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.repositories.DepartmentRepository;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void shouldReturnEmployeeWhenEmployeeExists() throws Exception {

        // Arrange

        Department department = departmentRepository.findById(2L).orElseThrow();

        Department savedDepartment =
                departmentRepository.save(department);

        Employee employee = Employee.builder().
                employeeCode("33").
                country("IN").
                firstName("vinay").
                lastName("magam").
                jobTitle("Software Engineer").
                email("ram@gmail.com").
                department(savedDepartment).
                hireDate(LocalDate.now()).
                build();

        // Set the required employee fields
        employee.setDepartment(savedDepartment);

        Employee savedEmployee =
                employeeRepository.save(employee);


        // Act + Assert

        mockMvc.perform(
                        get("/api/employees/" + savedEmployee.getId())
                )
                .andExpect(status().isOk());
    }
}