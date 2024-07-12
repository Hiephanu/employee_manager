package com.example.ncc_spring.service;

import com.example.ncc_spring.config.Config;
import com.example.ncc_spring.mapper.employee.EmployeeReqSaveMapper;
import com.example.ncc_spring.mapper.employee.EmployeeResDtoMapper;
import com.example.ncc_spring.model.dto.EmployeeReqSaveDto;
import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.repository.employee.EmployeeRepository;
import com.example.ncc_spring.service.employee.EmployeeCrudService;
import com.example.ncc_spring.service.employee.EmployeeCrudServiceInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = Config.class)
@WebMvcTest(EmployeeCrudServiceInterface.class)
public class EmployeeCrudServiceTest {
    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeResDtoMapper employeeResDtoMapper;
    @MockBean
    private EmployeeReqSaveMapper employeeReqSaveMapper;
    @Autowired
    private EmployeeCrudServiceInterface employeeCrudService;

    @Test
    public void testSaveEmployeeService() {
        // Tạo dữ liệu đầu vào giả để test
        EmployeeReqSaveDto mockReqData = EmployeeReqSaveDto.builder()
                .name("Đoàn Hiệp")
                .email("hiep@gmail.com")
                .build();

        //tạo employee
        Employee employee1 =Employee.builder()
                .e_name("Đoàn Hiệp")
                .e_email("hiep@gmail.com")
                .build();

        //tạo behaivor cho repo
        Mockito.when(employeeReqSaveMapper.mapToEmployee(mockReqData)).thenReturn(employee1);

        // Mock mapper để map từ DTO sang Entity
        Employee employee = employeeReqSaveMapper.mapToEmployee(mockReqData);

        // Stub phương thức save của repository
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        // Gọi phương thức cần test và nhận kết quả trả về
        Employee res = employeeCrudService.saveEmployee(mockReqData);
        // Kiểm tra kết quả
        if (res != null) {
            Assertions.assertEquals(mockReqData.getName(), res.getE_name());
        } else {
            Assertions.fail("Employee returned by saveEmployee is null");
        }
    }

}
