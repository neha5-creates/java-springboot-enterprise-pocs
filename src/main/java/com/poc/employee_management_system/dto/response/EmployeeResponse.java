package com.poc.employee_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
private Long id;
private String firstName;
private String lastName;
private String email;
private String phoneNumber;
private String jobTitle;
private Long departmentId;
private String departmentName;
private String managerName;
private Long managerId;
private LocalDate hireDate;
private BigDecimal salary;
private String status;
private LocalDateTime createdAt;
private LocalDateTime updatedAt;

}
