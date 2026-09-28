CREATE TABLE employee(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    job_title VARCHAR(100),
    salary NUMERIC(19,2) NOT NULL,
    hire_date DATE,
    status VARCHAR(20) NOT NULL,
    department_id BIGINT NOT NULL,
    manager_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_employee_email
                     UNIQUE(email),
    CONSTRAINT fk_employee_department
                     FOREIGN KEY (department_id)
                     REFERENCES department(id),
    CONSTRAINT fk_employee_manager
        FOREIGN KEY (manager_id)
            REFERENCES employee(id)
);


CREATE INDEX idx_employee_department
ON employee(department_id);

CREATE INDEX idx_employee_manager
ON employee(manager_id);

CREATE INDEX idx_employee_status
ON employee(status);