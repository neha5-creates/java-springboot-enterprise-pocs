package com.poc.employee_management_system.entity;

import com.poc.employee_management_system.audit.AuditableEntity;
import com.poc.employee_management_system.enums.EmploymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee", uniqueConstraints = { @UniqueConstraint(
        name= "uk_employee_email", columnNames ="email")
})
public class Employee extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="first_name", nullable = false, length=50)
    private String firstName;

    @Column(name ="last_name", nullable = false, length=50)
    private String lastName;

    @Column(name ="email", nullable = false, unique = true, length=100)
    private String email;
    @Column(name ="phone_number", length=20)
    private String phoneNumber;
    @Column(name ="job_title", nullable = false, length=50)
    private String jobTitle;

    @Column(name ="salary", nullable = false, length=50)
    private BigDecimal salary;

    @Column(name ="hire_date")
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name ="status", nullable = false, length=20)
    private EmploymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id",nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="manager_id")
    private Employee manager;

}

