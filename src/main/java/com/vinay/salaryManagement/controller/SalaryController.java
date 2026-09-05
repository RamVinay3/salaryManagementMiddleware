package com.vinay.salaryManagement.controller;

import com.vinay.salaryManagement.dto.response.SalaryResponse;
import com.vinay.salaryManagement.dto.request.SalaryUpdateRequest;
import com.vinay.salaryManagement.service.SalaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/{employeeId}/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping
    public ResponseEntity<SalaryResponse> getCurrentSalary(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(
                salaryService.getCurrentSalary(employeeId)
        );
    }

    @PostMapping
    public ResponseEntity<SalaryResponse> createSalary(
            @PathVariable Long employeeId,
            @Valid @RequestBody SalaryUpdateRequest request
    ) {
        SalaryResponse response =
                salaryService.createSalary(employeeId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<SalaryResponse>> salaryHistory(@PathVariable Long employeeId){

        List<SalaryResponse> response =
                salaryService.getSalaryHistory(employeeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}