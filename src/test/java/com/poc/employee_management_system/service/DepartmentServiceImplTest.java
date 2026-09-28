package com.poc.employee_management_system.service;

import com.poc.employee_management_system.dto.request.DepartmentCreateRequest;
import com.poc.employee_management_system.dto.response.DepartmentResponse;
import com.poc.employee_management_system.entity.Department;
import com.poc.employee_management_system.exception.DepartmentNotFoundException;
import com.poc.employee_management_system.exception.DuplicateDepartmentCodeException;
import com.poc.employee_management_system.mapper.DepartmentMapper;
import com.poc.employee_management_system.repository.DepartmentRepository;
import com.poc.employee_management_system.service.impl.DepartmentServiceImpl;
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
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void shouldReturnDepartmentWhenFound() {

        Long departmentId = 1L;

        Department department = new Department();
        department.setId(departmentId);
        department.setName("Engineering");

        DepartmentResponse response = new DepartmentResponse();
        response.setId(departmentId);
        response.setName("Engineering");

        when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.of(department));

        when(departmentMapper.toResponse(department))
                .thenReturn(response);

        DepartmentResponse result =
                departmentService.getDepartmentById(departmentId);

        assertNotNull(result);

        assertEquals(
                departmentId,
                result.getId()
        );

        assertEquals(
                "Engineering",
                result.getName()
        );

        verify(departmentRepository)
                .findById(departmentId);

        verify(departmentMapper)
                .toResponse(department);
    }

    @Test
    void shouldThrowExceptionWhenDepartmentNotFound() {

        Long departmentId = 999L;

        when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.empty());

        assertThrows(
                DepartmentNotFoundException.class,
                () -> departmentService.getDepartmentById(departmentId)
        );

        verify(departmentRepository)
                .findById(departmentId);
    }

    @Test
    void shouldThrowDuplicateCodeException() {

        DepartmentCreateRequest request =
                new DepartmentCreateRequest();

        request.setCode("ENG");

        when(departmentRepository.existsByCode("ENG"))
                .thenReturn(true);

        assertThrows(
                DuplicateDepartmentCodeException.class,
                () -> departmentService.createDepartment(request)
        );

        verify(departmentRepository)
                .existsByCode("ENG");
    }

    @Test
    void shouldCreateDepartmentSuccessfully() {

        DepartmentCreateRequest request =
                new DepartmentCreateRequest();

        request.setName("Engineering");
        request.setCode("ENG");

        Department department =
                new Department();

        Department savedDepartment =
                new Department();

        savedDepartment.setId(1L);
        savedDepartment.setName("Engineering");
        savedDepartment.setCode("ENG");

        DepartmentResponse response =
                new DepartmentResponse();

        response.setId(1L);
        response.setName("Engineering");
        response.setCode("ENG");

        when(departmentRepository.existsByCode("ENG"))
                .thenReturn(false);

        when(departmentMapper.toEntity(request))
                .thenReturn(department);

        when(departmentRepository.save(department))
                .thenReturn(savedDepartment);

        when(departmentMapper.toResponse(savedDepartment))
                .thenReturn(response);

        DepartmentResponse result =
                departmentService.createDepartment(request);

        assertNotNull(result);

        assertEquals(
                1L,
                result.getId()
        );

        assertEquals(
                "Engineering",
                result.getName()
        );

        assertEquals(
                "ENG",
                result.getCode()
        );

        verify(departmentRepository)
                .existsByCode("ENG");

        verify(departmentMapper)
                .toEntity(request);

        verify(departmentRepository)
                .save(department);

        verify(departmentMapper)
                .toResponse(savedDepartment);
    }
}