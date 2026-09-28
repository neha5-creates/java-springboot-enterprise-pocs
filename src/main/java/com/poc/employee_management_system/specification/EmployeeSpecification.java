package com.poc.employee_management_system.specification;

import com.poc.employee_management_system.dto.request.EmployeeSearchRequest;
import com.poc.employee_management_system.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> filter(EmployeeSearchRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Filter by name
            if (request.getName() != null && !request.getName().isBlank()) {

                String name = "%" + request.getName().toLowerCase() + "%";

                Predicate firstNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("firstName")),
                        name
                );

                Predicate lastNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lastName")),
                        name
                );

                predicates.add(
                        criteriaBuilder.or(
                                firstNamePredicate,
                                lastNamePredicate
                        )
                );
            }

            // Filter by department
            if (request.getDepartmentId() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("departmant").get("id"),
                                request.getDepartmentId()
                        )
                );
            }

            // Filter by employment status
            if (request.getStatus() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("status"),
                                request.getStatus()
                        )
                );
            }

            // Filter by manager
            if (request.getManagerId() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("manager").get("id"),
                                request.getManagerId()
                        )
                );
            }

            // Filter by hire date - from
            if (request.getHireDateFrom() != null) {

                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("hireDate"),
                                request.getHireDateFrom()
                        )
                );
            }

            // Filter by hire date - to
            if (request.getHireDateTo() != null) {

                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("hireDate"),
                                request.getHireDateTo()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}