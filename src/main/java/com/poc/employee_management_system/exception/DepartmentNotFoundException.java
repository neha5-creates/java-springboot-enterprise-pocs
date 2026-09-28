package com.poc.employee_management_system.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("Department with id" + id + "was not found");
    }
}
