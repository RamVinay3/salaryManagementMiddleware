package com.vinay.salaryManagement.controller;

import com.vinay.salaryManagement.dto.request.EmployeeCreateRequest;
import com.vinay.salaryManagement.dto.response.EmployeeResponse;
import com.vinay.salaryManagement.dto.request.EmployeeUpdateRequest;
import com.vinay.salaryManagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 25) Pageable pageable
    ) {
        return ResponseEntity.ok(
                employeeService.getEmployees(
                        departmentId,
                        country,
                        search,
                        pageable
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeCreateRequest request
    ) {
        EmployeeResponse response =
                employeeService.createEmployee(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request
    ) {
        return ResponseEntity.ok(
                employeeService.updateEmployee(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id
    ) {
        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}