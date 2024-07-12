package com.example.ncc_spring.mapper.employee;

import com.example.ncc_spring.model.dto.EmployeeReqSaveDto;
import com.example.ncc_spring.model.entity.Department;
import com.example.ncc_spring.model.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReqSaveMapper {
    public Employee mapToEmployee(EmployeeReqSaveDto employeeReqSaveDto) {
        return  Employee.builder()
                .e_name(employeeReqSaveDto.getName())
                .e_codeCheck(employeeReqSaveDto.getCodeCheck())
                .e_dob(employeeReqSaveDto.getDob())
                .e_position(employeeReqSaveDto.getPosition())
                .e_phoneNumber(employeeReqSaveDto.getPhoneNumber())
                .e_email(employeeReqSaveDto.getEmail())
                .department(Department.builder().id(employeeReqSaveDto.getId_department()).build())
                .e_salary(employeeReqSaveDto.getSalary())
                .e_address(employeeReqSaveDto.getAddress())
                .build();

    }
}
