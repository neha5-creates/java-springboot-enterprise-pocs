package com.poc.employee_management_system.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateDepartment() throws Exception {

        String requestBody = """
                {
                  "name": "Engineering",
                  "code": "FIN"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/departments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.name")
                                .value("Engineering")
                );
    }

    @Test
    void shouldCreateEmployeeEndToEnd() throws Exception {

        // 1. Create Department
        String departmentJson = """
                {
                  "name": "Engineering",
                  "code": "FIN"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/departments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(departmentJson)
                )
                .andExpect(status().isCreated());

        // 2. Create Employee
        String employeeJson = """
                {
                  "firstName": "Neha",
                  "lastName": "Gupta",
                  "email": "neha@example.com",
                  "phoneNumber": "9876543210",
                  "jobTitle": "Senior Software Engineer",
                  "salary": 1500000,
                  "departmentId": 1,
                  "hireDate": "2025-06-15",
                  "managerId": null
                }
                """;

        mockMvc.perform(
                        post("/api/v1/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(employeeJson)
                )
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.firstName")
                                .value("Neha")
                )
                .andExpect(
                        jsonPath("$.lastName")
                                .value("Gupta")
                )
                .andExpect(
                        jsonPath("$.email")
                                .value("neha@example.com")
                );
    }
}