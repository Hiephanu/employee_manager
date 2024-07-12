package com.example.ncc_spring.controller;

import com.example.ncc_spring.config.Config;
import com.example.ncc_spring.controller.employee.EmployeeCrudController;
import com.example.ncc_spring.model.dto.EmployeeReqSaveDto;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.service.employee.EmployeeCrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeCrudController.class)
@ContextConfiguration(classes = Config.class)
public class EmployeeCrudControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeCrudService employeeCrudService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void saveEmployee_ShouldIsBadRequest() throws Exception {
        Employee employee = Employee.builder()
                .id(1)
                .e_name("Hiep")
                .build();
        EmployeeReqSaveDto employeeReqSaveDto = EmployeeReqSaveDto.builder()
                        .name("Hiep")
                .build();
        Mockito.when(employeeCrudService.saveEmployee(employeeReqSaveDto)).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeReqSaveDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.phoneNumber").value("Phone number can't be null"))
                .andExpect(jsonPath("$.codeCheck").value("CodeCheck can't be null"))
                .andDo(print());
    }
    @Test
    public void saveEmployee_ShouldReturnUser() throws Exception {
        Employee employee = Employee.builder()
                .id(1)
                .e_name("Hiep")
                .build();
        EmployeeReqSaveDto employeeReqSaveDto = EmployeeReqSaveDto.builder()
                .name("Hiep")
                .codeCheck("1999")
                .phoneNumber("1000021")
                .build();

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeReqSaveDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Create success"))
                .andDo(print());
    }
}
