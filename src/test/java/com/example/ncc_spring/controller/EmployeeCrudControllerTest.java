package com.example.ncc_spring.controller;

import com.example.ncc_spring.config.Config;
import com.example.ncc_spring.controller.employee.EmployeeCrudController;
import com.example.ncc_spring.model.dto.EmployeeReqSaveDto;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.service.employee.EmployeeCrudService;
import com.example.ncc_spring.service.employee.UploadListEmployee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeCrudController.class)
//@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = Config.class)
public class EmployeeCrudControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeCrudService employeeCrudService;

    @MockBean
    private UploadListEmployee uploadListEmployee;
    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void saveEmployee_ShouldIsBadRequest() throws Exception {
//        Employee employee = Employee.builder()
//                .id(1)
//                .e_name("Hiep")
//                .build();
//        EmployeeReqSaveDto employeeReqSaveDto = EmployeeReqSaveDto.builder()
//                        .name("Hiep")
//                .build();
//        when(employeeCrudService.saveEmployee(employeeReqSaveDto)).thenReturn(employee);
//
//        mockMvc.perform(post("/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(employeeReqSaveDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.phoneNumber").value("Phone number can't be null"))
//                .andExpect(jsonPath("$.codeCheck").value("CodeCheck can't be null"))
//                .andDo(print());
//    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void saveEmployee_ShouldReturnSuccessMessage() throws Exception {
        // Chuẩn bị dữ liệu đầu vào
        EmployeeReqSaveDto employeeReqSaveDto = EmployeeReqSaveDto.builder()
                .name("Hiep")
                .codeCheck("1999")
                .phoneNumber("1000021")
                .build();

        // Mô phỏng hành vi của crudService.saveEmployee
        when(employeeCrudService.saveEmployee(employeeReqSaveDto)).thenReturn(Employee.builder().id(1).e_name("Hiep").build());

        // Gửi yêu cầu POST đến endpoint và kiểm tra kết quả
        mockMvc.perform(post("/employees")
                        .with(csrf())  // Bổ sung CSRF token
                        .with(user("admin").roles("ADMIN"))  // Giả lập user có role ADMIN
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeReqSaveDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.status").value(200))
                .andDo(print());
    }
}
