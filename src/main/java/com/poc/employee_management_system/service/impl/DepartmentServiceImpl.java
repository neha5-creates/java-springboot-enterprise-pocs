package com.poc.employee_management_system.service.impl;
import com.poc.employee_management_system.dto.request.DepartmentCreateRequest;
import com.poc.employee_management_system.dto.response.DepartmentResponse;
import com.poc.employee_management_system.entity.Department;
import com.poc.employee_management_system.exception.DepartmentNotFoundException;
import com.poc.employee_management_system.exception.DuplicateDepartmentCodeException;
import com.poc.employee_management_system.mapper.DepartmentMapper;
import com.poc.employee_management_system.repository.DepartmentRepository;
import com.poc.employee_management_system.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class DepartmentServiceImpl
        implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }
    @Override
    public DepartmentResponse createDepartment(
            DepartmentCreateRequest request) {
        if (departmentRepository.existsByCode(
                request.getCode())) {
            throw new DuplicateDepartmentCodeException(
                    request.getCode()
            );
        }
        Department department =
                departmentMapper.toEntity(request);
        Department savedDepartment =
                departmentRepository.save(department);
        return departmentMapper.toResponse(
                savedDepartment
        );
    }
    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(
            Long id) {
        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(
                                () ->
                                        new DepartmentNotFoundException(id)
                        );
        return departmentMapper.toResponse(
                department
        );
    }
}
