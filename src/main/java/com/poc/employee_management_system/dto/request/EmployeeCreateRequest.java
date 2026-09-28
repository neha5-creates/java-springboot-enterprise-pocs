package com.poc.employee_management_system.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeCreateRequest {

@NotBlank
    @Size(min=2, max=50)
    private String firstName;

@NotBlank
    @Size(min=2, max= 50)
    private String lastName;

@NotBlank
    @Email
    private String email;

private String phoneNumber;
private String jobTitle;
@NotNull
    @Positive
    private BigDecimal salary;
@NotNull
    private Long departmentId;

@PastOrPresent
    private LocalDate hireDate;

private Long managerId;


}
