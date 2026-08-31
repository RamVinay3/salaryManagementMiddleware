package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Department not found: " + id));
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
}
