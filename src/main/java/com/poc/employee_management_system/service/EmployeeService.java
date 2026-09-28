package com.poc.employee_management_system.service;

import com.poc.employee_management_system.dto.request.EmployeeCreateRequest;
import com.poc.employee_management_system.dto.request.EmployeeSearchRequest;
import com.poc.employee_management_system.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeCreateRequest request);
    EmployeeResponse getEmployeeById(Long id);
    Page<EmployeeResponse> getEmployees(EmployeeSearchRequest searchRequest, Pageable pageable);
    EmployeeResponse updateEmployee(Long id, EmployeeCreateRequest request);
    void deleteEmployee(Long id);
}
