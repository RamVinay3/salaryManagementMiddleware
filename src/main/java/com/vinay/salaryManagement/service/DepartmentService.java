package com.vinay.salaryManagement.service;

import com.vinay.salaryManagement.dto.request.DepartmentCreateRequest;
import com.vinay.salaryManagement.dto.response.DepartmentResponse;
import com.vinay.salaryManagement.dto.request.DepartmentUpdateRequest;
import com.vinay.salaryManagement.entity.Department;
import com.vinay.salaryManagement.exception.DepartmentNotFoundException;
import com.vinay.salaryManagement.mapper.DepartmentMapper;
import com.vinay.salaryManagement.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(id)
                );

        return departmentMapper.toResponse(department);
    }

    public DepartmentResponse createDepartment(
            DepartmentCreateRequest request
    ) {

        if (departmentRepository.existsByNameIgnoreCase(
                request.getName()
        )) {
            throw new IllegalArgumentException(
                    "Department already exists: "
                            + request.getName()
            );
        }

        Department department =
                departmentMapper.toEntity(request);

        Department savedDepartment =
                departmentRepository.save(department);

        return departmentMapper.toResponse(savedDepartment);
    }

    public DepartmentResponse updateDepartment(
            Long id,
            DepartmentUpdateRequest request
    ) {

        Department department = departmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(id)
                );

        departmentMapper.updateEntity(
                department,
                request
        );

        Department updatedDepartment =
                departmentRepository.save(department);

        return departmentMapper.toResponse(updatedDepartment);
    }

    public void deleteDepartment(Long id) {

        Department department = departmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(id)
                );

        departmentRepository.delete(department);
    }
}