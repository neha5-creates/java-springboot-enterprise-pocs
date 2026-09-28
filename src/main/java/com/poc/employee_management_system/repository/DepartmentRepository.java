package com.poc.employee_management_system.repository;

import com.poc.employee_management_system.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
Optional<Department> findByCode(String code);
boolean existsByCode(String code);
}
