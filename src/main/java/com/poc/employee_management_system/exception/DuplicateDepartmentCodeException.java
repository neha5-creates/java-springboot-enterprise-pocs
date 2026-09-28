package com.poc.employee_management_system.exception;

public class DuplicateDepartmentCodeException extends RuntimeException{
    public DuplicateDepartmentCodeException(String code) {
        super("A department with code " + code + "already exists");
    }
}
