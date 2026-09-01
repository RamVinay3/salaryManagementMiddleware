package com.vinay.salaryManagement.mapper;


import com.vinay.salaryManagement.dto.request.DepartmentCreateRequest;
import com.vinay.salaryManagement.dto.response.DepartmentResponse;
import com.vinay.salaryManagement.dto.request.DepartmentUpdateRequest;
import com.vinay.salaryManagement.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(
            DepartmentCreateRequest request
    ) {
        return Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(
            Department department,
            DepartmentUpdateRequest request
    ) {
        department.setName(request.getName());
        department.setDescription(request.getDescription());
    }

    public DepartmentResponse toResponse(
            Department department
    ) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}
