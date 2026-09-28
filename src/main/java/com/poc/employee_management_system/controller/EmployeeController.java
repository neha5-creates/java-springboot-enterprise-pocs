package com.poc.employee_management_system.controller;

import com.poc.employee_management_system.dto.request.EmployeeCreateRequest;
import com.poc.employee_management_system.dto.request.EmployeeSearchRequest;
import com.poc.employee_management_system.dto.response.EmployeeResponse;
import com.poc.employee_management_system.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(
            @Valid
            @RequestBody
            EmployeeCreateRequest request) {

        return employeeService.createEmployee(request);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(
            @PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }


    @GetMapping
    public Page<EmployeeResponse> getEmployees(
            EmployeeSearchRequest searchRequest,
            Pageable pageable) {

        return employeeService.getEmployees(
                searchRequest,
                pageable
        );
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeCreateRequest request) {

        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}