package com.poc.employee_management_system.controller;
import tools.jackson.databind.ObjectMapper;
import com.poc.employee_management_system.dto.request.EmployeeCreateRequest;
import com.poc.employee_management_system.dto.response.EmployeeResponse;
import com.poc.employee_management_system.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void shouldCreateEmployeeSuccessfully() throws Exception {

        EmployeeCreateRequest request =
                new EmployeeCreateRequest();

        request.setFirstName("Rahul");
        request.setLastName("Sharma");
        request.setEmail("rahul@example.com");
        request.setPhoneNumber("9876543210");
        request.setJobTitle("Software Engineer");
        request.setSalary(new BigDecimal("75000"));
        request.setDepartmentId(1L);
        request.setHireDate(LocalDate.of(2025, 6, 15));
        request.setManagerId(null);

        EmployeeResponse response =
                new EmployeeResponse();

        response.setId(1L);
        response.setFirstName("Rahul");
        response.setLastName("Sharma");
        response.setEmail("rahul@example.com");
        response.setJobTitle("Software Engineer");
        response.setSalary(new BigDecimal("75000"));

        when(employeeService.createEmployee(any(EmployeeCreateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Rahul"))
                .andExpect(jsonPath("$.lastName").value("Sharma"))
                .andExpect(jsonPath("$.email").value("rahul@example.com"));
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        EmployeeCreateRequest request =
                new EmployeeCreateRequest();
        request.setFirstName("Neha");
        request.setLastName("Gupta");
        request.setEmail("neha@example.com");
        request.setSalary(
                BigDecimal.valueOf(1500000));
        request.setHireDate(LocalDate.now());
        request.setDepartmentId(1L);
        EmployeeResponse response =
                new EmployeeResponse();
        response.setId(1L);
        response.setFirstName("Neha");
        when(employeeService.createEmployee(
                any(EmployeeCreateRequest.class)))
                .thenReturn(response);
        mockMvc.perform(
                        post("/api/v1/employees")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request)))
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.id")
                                .value(1))
                .andExpect(
                        jsonPath("$.firstName")
                                .value("Neha"));
    }

    @Test
    void shouldReturnBadRequestWhenValidationFails()
            throws Exception {
        EmployeeCreateRequest request =
                new EmployeeCreateRequest();
        request.setEmail("wrong-email");
        mockMvc.perform(
                        post("/api/v1/employees")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request)))
                .andExpect(
                        status().isBadRequest());
    }

    @Test
    void shouldReturnEmployee()
            throws Exception {
        EmployeeResponse response =
                new EmployeeResponse();
        response.setId(1L);
        response.setFirstName("Neha");
        when(employeeService.getEmployeeById(1L))
                .thenReturn(response);
        mockMvc.perform(
                        org.springframework.test.web.servlet.request
                                .MockMvcRequestBuilders
                                .get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(1))
                .andExpect(
                        jsonPath("$.firstName")
                                .value("Neha"));
    }

}