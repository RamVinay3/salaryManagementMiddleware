package com.vinay.salaryManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
