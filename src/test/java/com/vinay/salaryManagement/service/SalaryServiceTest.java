package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.dto.request.SalaryUpdateRequest;
import com.vinay.salaryManagement.dto.response.SalaryResponse;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.entity.Salary;
import com.vinay.salaryManagement.exception.EmployeeNotFoundException;
import com.vinay.salaryManagement.exception.SalaryNotFoundException;
import com.vinay.salaryManagement.mapper.SalaryMapper;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import com.vinay.salaryManagement.repositories.SalaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryServiceTest {

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryMapper salaryMapper;

    @InjectMocks
    private SalaryService salaryService;

    @Test
    void shouldThrowEmployeeNotFoundWhenEmployeeDoesNotExist() {

        // ARRANGE

        when(employeeRepository.existsById(999L))
                .thenReturn(false);


        // ACT + ASSERT

        assertThrows(
                EmployeeNotFoundException.class,
                () -> salaryService.getCurrentSalary(999L)
        );
    }

    @Test
    void shouldThrowSalaryNotFoundWhenEmployeeHasNoSalary() {

        // ARRANGE

        when(employeeRepository.existsById(1L))
                .thenReturn(true);

        when(salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.empty());


        // ACT + ASSERT

        assertThrows(
                SalaryNotFoundException.class,
                () -> salaryService.getCurrentSalary(1L)
        );
    }
        @Test
        void shouldReturnCurrentSalary() {

            // ARRANGE

            Employee employee = new Employee();
            employee.setId(1L);

            Salary salary = new Salary();
            salary.setId(10L);
            salary.setEmployee(employee);

            SalaryResponse response = new SalaryResponse();
            response.setId(10L);

            when(employeeRepository.existsById(1L))
                    .thenReturn(true);

            when(salaryRepository
                    .findTopByEmployeeIdOrderByEffectiveDateDesc(1L))
                    .thenReturn(Optional.of(salary));

            when(salaryMapper.toResponse(salary))
                    .thenReturn(response);


            // ACT

            SalaryResponse result =
                    salaryService.getCurrentSalary(1L);


            // ASSERT

            assertEquals(10L, result.getId());
            verify(salaryRepository)
                    .findTopByEmployeeIdOrderByEffectiveDateDesc(1L);
        }


    @Test
    void shouldCreateSalarySuccessfully() {

        // ARRANGE

        SalaryUpdateRequest request = new SalaryUpdateRequest();

        Employee employee = new Employee();
        employee.setId(1L);

        Salary salary = new Salary();

        Salary savedSalary = new Salary();
        savedSalary.setId(10L);

        SalaryResponse response = new SalaryResponse();
        response.setId(10L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(salaryMapper.toEntity(request, employee))
                .thenReturn(salary);

        when(salaryRepository.save(salary))
                .thenReturn(savedSalary);

        when(salaryMapper.toResponse(savedSalary))
                .thenReturn(response);


        // ACT

        SalaryResponse result =
                salaryService.createSalary(1L, request);


        // ASSERT

        assertEquals(10L, result.getId());

        verify(salaryRepository)
                .save(salary);
    }


    }

