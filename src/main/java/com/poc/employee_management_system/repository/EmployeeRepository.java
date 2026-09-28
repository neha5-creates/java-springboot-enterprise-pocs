package com.poc.employee_management_system.repository;

import com.poc.employee_management_system.entity.Employee;
import com.poc.employee_management_system.enums.EmploymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
Optional<Employee> findByEmail(String email);
boolean existsByEmail(String email);

Page<Employee> findByStatus(EmploymentStatus status, Pageable pageable);
Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

}
