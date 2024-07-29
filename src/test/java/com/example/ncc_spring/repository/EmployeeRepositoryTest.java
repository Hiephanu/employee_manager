package com.example.ncc_spring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testFindByEEmail() {
        Employee employee = new Employee();
        employee.setE_email("doan@gmail.com");

        employeeRepository.save(employee);

        Optional<Employee> foundEmployee = employeeRepository.findByEEmail("doan@gmail.com");

        if(foundEmployee.isPresent()) {
            System.out.println(foundEmployee.get().getE_email());
        } else {
            System.out.println("Empty");
        }
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getE_email()).isEqualTo("doan@gmail.com");
    }

    @Test
    public void testFindAllByEName() {
        Employee employee = new Employee();
        employee.setE_name("John Doe");
        employeeRepository.save(employee);

        Pageable pageable = PageRequest.of(0, 10);
        List<Employee> employees = employeeRepository.findAllByEName("John", pageable);
        System.out.println(employees.size());
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getE_name()).isEqualTo("John Doe");
    }
}
