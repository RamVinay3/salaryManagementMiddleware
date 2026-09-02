package com.vinay.salaryManagement.service;


import com.vinay.salaryManagement.dto.request.EmployeeCreateRequest;
import com.vinay.salaryManagement.dto.response.EmployeeResponse;
import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.entity.Employee;
import com.vinay.salaryManagement.exception.DepartmentNotFoundException;
import com.vinay.salaryManagement.exception.EmployeeNotFoundException;
import com.vinay.salaryManagement.mapper.EmployeeMapper;
import com.vinay.salaryManagement.repositories.DepartmentRepository;
import com.vinay.salaryManagement.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private  EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldReturnEmployeeWhenEmployeeExist(){
        Employee employee= new Employee();
        employee.setId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse e = employeeService.getEmployeeById(1L);
        assertEquals(1L,e.getId());


    }

    @Test
    void shouldThrowExceptionWhenEmployeeDoesNotExist() {

        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(999L)
        );
    }

    @Test
    void shouldCallRepositoryOnce() {

        Employee employee = new Employee();
        employee.setId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        employeeService.getEmployeeById(1L);

        verify(employeeRepository).findById(1L);
    }

    @Test
    void shouldCreateEmployeeSuccessfully() {

        // ---------- ARRANGE ----------

        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setDepartmentId(1L);

        Department department = new Department();
        department.setId(1L);

        Employee employee = new Employee();

        Employee savedEmployee = new Employee();
        savedEmployee.setId(100L);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(100L);


        // Fake department lookup
        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        // Fake mapper
        when(employeeMapper.toEntity(request, department))
                .thenReturn(employee);

        // Fake database save
        when(employeeRepository.save(employee))
                .thenReturn(savedEmployee);

        // Fake response mapping
        when(employeeMapper.toResponse(savedEmployee))
                .thenReturn(response);


        // ---------- ACT ----------

        EmployeeResponse result =
                employeeService.createEmployee(request);


        // ---------- ASSERT ----------
        verify(employeeRepository)
                .save(employee);
        assertEquals(100L, result.getId());
    }
    @Test
    void shouldThrowExceptionWhenDepartmentDoesNotExist() {

        // ARRANGE

        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setDepartmentId(999L);

        when(departmentRepository.findById(999L))
                .thenReturn(Optional.empty());


        // ACT + ASSERT
        verify(employeeMapper,never()).toEntity(any(),any());
        verify(employeeRepository,never()).save(any());
        assertThrows(
                DepartmentNotFoundException.class,
                () -> employeeService.createEmployee(request)
        );
    }

}
