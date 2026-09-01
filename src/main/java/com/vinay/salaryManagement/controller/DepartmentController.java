package com.vinay.salaryManagement.controller;

import com.vinay.salaryManagement.dto.request.DepartmentCreateRequest;
import com.vinay.salaryManagement.dto.response.DepartmentResponse;
import com.vinay.salaryManagement.dto.request.DepartmentUpdateRequest;
import com.vinay.salaryManagement.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>>
    getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse>
    getDepartmentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                departmentService.getDepartmentById(id)
        );
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse>
    createDepartment(
            @Valid @RequestBody DepartmentCreateRequest request
    ) {

        DepartmentResponse response =
                departmentService.createDepartment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse>
    updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentUpdateRequest request
    ) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id
    ) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}