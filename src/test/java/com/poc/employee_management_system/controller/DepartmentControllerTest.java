package com.poc.employee_management_system.controller;

import com.poc.employee_management_system.dto.request.DepartmentCreateRequest;
import com.poc.employee_management_system.dto.response.DepartmentResponse;
import com.poc.employee_management_system.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DepartmentService departmentService;

    @Test
    void shouldCreateDepartment() throws Exception {

        DepartmentCreateRequest request =
                new DepartmentCreateRequest();

        request.setName("Engineering");
        request.setCode("ENG");

        DepartmentResponse response =
                new DepartmentResponse();

        response.setId(1L);
        response.setName("Engineering");

        when(departmentService.createDepartment(
                any(DepartmentCreateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/departments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    void shouldReturnDepartment() throws Exception {

        DepartmentResponse response =
                new DepartmentResponse();

        response.setId(1L);
        response.setName("Engineering");

        when(departmentService.getDepartmentById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/departments/1")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.name")
                                .value("Engineering")
                );
    }
}