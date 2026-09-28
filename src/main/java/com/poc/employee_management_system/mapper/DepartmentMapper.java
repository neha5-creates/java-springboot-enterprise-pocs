package com.poc.employee_management_system.mapper;

import com.poc.employee_management_system.dto.request.DepartmentCreateRequest;
import com.poc.employee_management_system.dto.response.DepartmentResponse;
import com.poc.employee_management_system.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
public Department toEntity(DepartmentCreateRequest request) {
    Department department = new Department();
    department.setName(request.getName());
    department.setCode(request.getCode());
return department;

}

public DepartmentResponse toResponse(Department department) {
    DepartmentResponse response = new DepartmentResponse();
    response.setId(department.getId());
    response.setName(department.getName());
    response.setCode(department.getCode());
    response.setCreatedAt(department.getCreatedAt());
    response.setUpdatedAt(department.getUpdatedAt());
    return response;
}
}
