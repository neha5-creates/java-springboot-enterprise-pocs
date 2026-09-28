package com.poc.employee_management_system.mapper;

import com.poc.employee_management_system.dto.request.EmployeeCreateRequest;
import com.poc.employee_management_system.dto.response.EmployeeResponse;
import com.poc.employee_management_system.entity.Department;
import com.poc.employee_management_system.entity.Employee;
import com.poc.employee_management_system.enums.EmploymentStatus;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(
            EmployeeCreateRequest request,
            Department department,
            Employee manager) {

        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .jobTitle(request.getJobTitle())
                .salary(request.getSalary())
                .hireDate(request.getHireDate())
                .department(department)
                .manager(manager)
                .status(EmploymentStatus.ACTIVE)
                .build();
    }

    public EmployeeResponse toResponse(Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .jobTitle(employee.getJobTitle())
                .hireDate(employee.getHireDate())
                .salary(employee.getSalary())
                .status(employee.getStatus().name())
                .departmentName(
                        employee.getDepartment() != null
                                ? employee.getDepartment().getName()
                                : null
                )
                .managerId(
                        employee.getManager() != null
                                ? employee.getManager().getId()
                                : null
                )
                .managerName(
                        employee.getManager() != null
                                ? employee.getManager().getFirstName()
                                + " "
                                + employee.getManager().getLastName()
                                : null
                )
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}