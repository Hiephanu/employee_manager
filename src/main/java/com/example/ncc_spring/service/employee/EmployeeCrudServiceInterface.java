package com.example.ncc_spring.service.employee;

import com.example.ncc_spring.model.dto.EmployeeReqSaveDto;
import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.model.dto.UpdateEmployeeReqDto;
import com.example.ncc_spring.model.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeCrudServiceInterface {
    public Page<EmployeeResDto> getAllEmployee(int page, int perPage);
    public EmployeeResDto getEmployeeByEmail(String email);
    public List<Employee> getAllEmployeeNotPagination();
    public Employee saveEmployee(EmployeeReqSaveDto employeeReqSaveDto);
    public void deleteEmployee(long e_id);
    public void updateEmployee(UpdateEmployeeReqDto updateEmployeeReqDto);
    public EmployeeResDto getEmployeeById(long id);
}
