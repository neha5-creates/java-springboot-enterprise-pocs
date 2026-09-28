package com.poc.employee_management_system.dto.response;

import java.time.LocalDateTime;
import lombok.Data;


@Data
public class DepartmentResponse {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
