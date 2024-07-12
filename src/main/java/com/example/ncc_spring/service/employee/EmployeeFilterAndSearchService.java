package com.example.ncc_spring.service.employee;

import com.example.ncc_spring.mapper.employee.EmployeeResDtoMapper;
import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.repository.employee.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeFilterAndSearchService {
    private final EmployeeResDtoMapper employeeResDtoMapper;
    private  final EmployeeRepository employeeRepository;

    public List<EmployeeResDto> findByName(String keyWord, int page, int perPage) {
        return employeeRepository.findAllByEName(keyWord, PageRequest.of(page, perPage)).stream().map(employeeResDtoMapper::mapToEmployeeResDto).toList();
    }

    public List<EmployeeResDto> getEmployeeByDepartment(long departmentId, int page, int perPage) {
        return employeeRepository.findAllByDepartmentId(departmentId,PageRequest.of(page, perPage)).stream().map(employeeResDtoMapper::mapToEmployeeResDto).toList();
    }
}
