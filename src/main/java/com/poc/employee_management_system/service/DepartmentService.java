package com.poc.employee_management_system.service;

import com.poc.employee_management_system.dto.request.DepartmentCreateRequest;
import com.poc.employee_management_system.dto.response.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentCreateRequest request);

    DepartmentResponse getDepartmentById(Long id);
}
