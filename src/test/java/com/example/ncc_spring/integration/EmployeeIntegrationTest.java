package com.example.ncc_spring.integration;

import com.example.ncc_spring.controller.employee.EmployeeSearchAndFilterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@WebMvcTest(EmployeeSearchAndFilterController.class)
public class EmployeeIntegrationTest {
    @MockBean
    private EmployeeSearchAndFilterController employeeSearchAndFilterController;
    @Autowired
    private MockMvc mockMvc;


}
