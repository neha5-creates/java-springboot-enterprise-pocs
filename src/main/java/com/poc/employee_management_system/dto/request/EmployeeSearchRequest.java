package com.poc.employee_management_system.dto.request;

import com.poc.employee_management_system.enums.EmploymentStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class EmployeeSearchRequest {
private String name;
private Long departmentId;
private EmploymentStatus status;
private Long managerId;
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate hireDateFrom;
@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate hireDateTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public void setStatus(EmploymentStatus status) {
        this.status = status;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public LocalDate getHireDateFrom() {
        return hireDateFrom;
    }

    public void setHireDateFrom(LocalDate hireDateFrom) {
        this.hireDateFrom = hireDateFrom;
    }

    public LocalDate getHireDateTo() {
        return hireDateTo;
    }

    public void setHireDateTo(LocalDate hireDateTo) {
        this.hireDateTo = hireDateTo;
    }
}
