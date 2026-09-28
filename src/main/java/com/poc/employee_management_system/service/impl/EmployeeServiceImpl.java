package com.poc.employee_management_system.service.impl;

import com.poc.employee_management_system.dto.request.EmployeeCreateRequest;
import com.poc.employee_management_system.dto.request.EmployeeSearchRequest;
import com.poc.employee_management_system.dto.response.EmployeeResponse;
import com.poc.employee_management_system.entity.Department;
import com.poc.employee_management_system.entity.Employee;
import com.poc.employee_management_system.exception.DepartmentNotFoundException;
import com.poc.employee_management_system.exception.DuplicateEmailException;
import com.poc.employee_management_system.exception.EmployeeNotFoundException;
import com.poc.employee_management_system.exception.InvalidRequestException;
import com.poc.employee_management_system.exception.ManagerNotFoundException;
import com.poc.employee_management_system.mapper.EmployeeMapper;
import com.poc.employee_management_system.repository.DepartmentRepository;
import com.poc.employee_management_system.repository.EmployeeRepository;
import com.poc.employee_management_system.service.EmployeeService;
import com.poc.employee_management_system.specification.EmployeeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            EmployeeMapper employeeMapper) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeResponse createEmployee(
            EmployeeCreateRequest request) {

        validateUniqueEmail(request.getEmail());

        Department department =
                findDepartmentById(request.getDepartmentId());

        Employee manager =
                findManagerIfProvided(request.getManagerId());

        Employee employee =
                employeeMapper.toEntity(
                        request,
                        department,
                        manager
                );

        Employee savedEmployee =
                employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(
                                () ->
                                        new EmployeeNotFoundException(id)
                        );

        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getEmployees(
            EmployeeSearchRequest searchRequest,
            Pageable pageable) {

        validateHireDateRange(searchRequest);

        Specification<Employee> specification =
                EmployeeSpecification.filter(searchRequest);

        Page<Employee> employeePage =
                employeeRepository.findAll(
                        specification,
                        pageable
                );

        return employeePage.map(
                employeeMapper::toResponse
        );
    }

    @Override
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeCreateRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        Department department = findDepartmentById(
                request.getDepartmentId());

        Employee manager = findManagerIfProvided(
                request.getManagerId());

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setJobTitle(request.getJobTitle());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());
        employee.setDepartment(department);
        employee.setManager(manager);

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return employeeMapper.toResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeRepository.delete(employee);
    }

    private void validateUniqueEmail(String email) {

        if (employeeRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
    }

    private Department findDepartmentById(Long departmentId) {

        return departmentRepository
                .findById(departmentId)
                .orElseThrow(
                        () ->
                                new DepartmentNotFoundException(
                                        departmentId
                                )
                );
    }

    private Employee findManagerIfProvided(Long managerId) {

        if (managerId == null) {
            return null;
        }

        return employeeRepository
                .findById(managerId)
                .orElseThrow(
                        () ->
                                new ManagerNotFoundException(
                                        managerId
                                )
                );
    }

    private void validateHireDateRange(
            EmployeeSearchRequest request) {

        if (request.getHireDateFrom() != null
                && request.getHireDateTo() != null
                && request.getHireDateFrom()
                .isAfter(request.getHireDateTo())) {

            throw new InvalidRequestException(
                    "hireDateFrom must not be after hireDateTo"
            );
        }
    }
}