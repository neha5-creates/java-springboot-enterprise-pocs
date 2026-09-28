package com.poc.employee_management_system.service;

import com.poc.employee_management_system.dto.request.EmployeeCreateRequest;
import com.poc.employee_management_system.dto.response.EmployeeResponse;
import com.poc.employee_management_system.entity.Employee;
import com.poc.employee_management_system.exception.DuplicateEmailException;
import com.poc.employee_management_system.exception.EmployeeNotFoundException;
import com.poc.employee_management_system.mapper.EmployeeMapper;
import com.poc.employee_management_system.repository.DepartmentRepository;
import com.poc.employee_management_system.repository.EmployeeRepository;
import com.poc.employee_management_system.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void shouldReturnEmployeeWhenFound() {

        Long employeeId = 1L;

        Employee employee = new Employee();
        employee.setId(employeeId);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(employeeId);

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result =
                employeeService.getEmployeeById(employeeId);

        assertNotNull(result);

        assertEquals(
                employeeId,
                result.getId()
        );

        verify(employeeRepository)
                .findById(employeeId);

        verify(employeeMapper)
                .toResponse(employee);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {

        Long employeeId = 999L;

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(employeeId)
        );

        verify(employeeRepository)
                .findById(employeeId);
    }

    @Test
    void shouldThrowDuplicateEmailException() {

        EmployeeCreateRequest request =
                new EmployeeCreateRequest();

        request.setEmail("neha@example.com");

        when(employeeRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(true);

        assertThrows(
                DuplicateEmailException.class,
                () -> employeeService.createEmployee(request)
        );

        verify(employeeRepository)
                .existsByEmail(request.getEmail());
    }
}