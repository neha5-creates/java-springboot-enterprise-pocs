package com.poc.employee_management_system.exception;

public class ManagerNotFoundException extends RuntimeException{
    public ManagerNotFoundException(Long id) {
        super("Manager with employee id " + id + "was not found");
    }
}
