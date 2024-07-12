package com.example.ncc_spring.mapper.employee;

import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.model.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeResDtoMapper {
    public EmployeeResDto mapToEmployeeResDto(Employee employee) {
        return  EmployeeResDto.builder()
                .id(employee.getId())
                .dob(employee.getE_dob())
                .name(employee.getE_name())
                .email(employee.getE_email())
                .codeCheck(employee.getE_codeCheck())
                .departmentId(employee.getDepartment().getId())
                .salary(employee.getE_salary())
                .phoneNumber(employee.getE_phoneNumber())
                .address(employee.getE_address())
                .position(employee.getE_position())
                .build();


    }
}
