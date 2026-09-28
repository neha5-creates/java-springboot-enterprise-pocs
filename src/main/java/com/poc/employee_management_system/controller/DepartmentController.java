package com.poc.employee_management_system.controller;

import com.poc.employee_management_system.dto.request.DepartmentCreateRequest;
import com.poc.employee_management_system.dto.response.DepartmentResponse;
import com.poc.employee_management_system.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService =departmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(
            @Valid
            @RequestBody DepartmentCreateRequest request) {
        return departmentService.createDepartment(request);
    }

    @GetMapping("/{id}")
    public DepartmentResponse getDepartmentById( @PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }
}
