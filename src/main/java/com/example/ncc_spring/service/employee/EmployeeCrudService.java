package com.example.ncc_spring.service.employee;

import com.example.ncc_spring.exception.ExceptionEntity.BadRequestException;
import com.example.ncc_spring.exception.ExceptionEntity.NotFoundException;
import com.example.ncc_spring.mapper.employee.EmployeeReqSaveMapper;
import com.example.ncc_spring.mapper.employee.EmployeeResDtoMapper;
import com.example.ncc_spring.model.dto.EmployeeReqSaveDto;
import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.model.dto.UpdateEmployeeReqDto;
import com.example.ncc_spring.model.entity.Department;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.repository.employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeCrudService implements EmployeeCrudServiceInterface{
    private  final Logger logger =  LoggerFactory.getLogger(EmployeeCrudService.class);
    private EmployeeRepository employeeRepository;
    private final EmployeeResDtoMapper employeeResDtoMapper;
    private final EmployeeReqSaveMapper employeeReqSaveMapper;

    @Autowired
    private void setEmployeeRepository(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    public Page<EmployeeResDto> getAllEmployee(int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Employee> employeesPage = employeeRepository.findAll(pageable);
        return employeesPage.map(employeeResDtoMapper::mapToEmployeeResDto);
    }

    public EmployeeResDto getEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByEEmail(email);
        if(employee.isEmpty()) {
            throw new NotFoundException("Employee not found");
        }
        return employeeResDtoMapper.mapToEmployeeResDto(employee.get());
    }
    public List<Employee> getAllEmployeeNotPagination() {
        return employeeRepository.findAll();
    }
    public EmployeeResDto getEmployeeById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            return  employeeResDtoMapper.mapToEmployeeResDto(employee.get());
        } else {
            throw  new NotFoundException("Employee not found");
        }
    }

    @Transactional
    public Employee saveEmployee(EmployeeReqSaveDto employeeReqSaveDto) {
        if(employeeReqSaveDto != null) {
            return employeeRepository.save(employeeReqSaveMapper.mapToEmployee(employeeReqSaveDto));
        } else {
            throw  new BadRequestException("Cannot found employee data");
        }
    }

    public void deleteEmployee(long e_id) {
        employeeRepository.deleteById(e_id);
    }

    public void updateEmployee(UpdateEmployeeReqDto updateEmployeeReqDto) {
        Optional<Employee> employee = employeeRepository.findById(updateEmployeeReqDto.getId());
        if(employee.isPresent()) {
            employee.get().setE_name(updateEmployeeReqDto.getName());
            employee.get().setE_codeCheck(updateEmployeeReqDto.getCodeCheck());
            employee.get().setE_dob(updateEmployeeReqDto.getDob());
            employee.get().setE_email(updateEmployeeReqDto.getEmail());
            employee.get().setDepartment(Department.builder().id(updateEmployeeReqDto.getId_department()).build());
            employee.get().setE_position(updateEmployeeReqDto.getPosition());
            employee.get().setE_salary(updateEmployeeReqDto.getSalary());
            employee.get().setE_address(updateEmployeeReqDto.getAddress());
            employee.get().setE_updatedAt(LocalDate.now());

            employeeRepository.save(employee.get());
        } else {
            throw new NotFoundException("Employee not found");
        }
    }

}
