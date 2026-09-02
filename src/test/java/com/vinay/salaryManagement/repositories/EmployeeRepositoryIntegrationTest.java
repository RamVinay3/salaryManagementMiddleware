package com.vinay.salaryManagement.repositories;

import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryIntegrationTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldSaveAndFindEmployee() {

        Department department= Department.builder().id(1L).name("Engineering").build();


        Employee employee = Employee.builder().
                employeeCode("31").
                country("IN").
                firstName("vinay").
                lastName("magam").
                jobTitle("Software Engineer").
                email("ramvinay609@gmail.com").
                department(department).
                hireDate(LocalDate.now()).
                build();





        Employee savedEmployee =
                employeeRepository.save(employee);

        assertNotNull(savedEmployee.getId());

        Employee foundEmployee =
                employeeRepository.findById(
                        savedEmployee.getId()
                ).orElseThrow(()-> new EmployeeNotFoundException(savedEmployee.getId()));

        assertEquals(
                savedEmployee.getId(),
                foundEmployee.getId()
        );
    }
}