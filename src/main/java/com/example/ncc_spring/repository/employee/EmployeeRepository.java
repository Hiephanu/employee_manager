package com.example.ncc_spring.repository.employee;

import com.example.ncc_spring.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM employee e where e.e_name like %:keyword%")
    List<Employee> findAllByEName(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT e FROM employee e where e.e_email = :email")
    Optional<Employee> findByEEmail(@Param("email") String email);

    @Query("SELECT e FROM employee e where e.id = :id")
    Optional<Employee> findById(@Param("id") long id);
    List<Employee> findAllByDepartmentId(long id, Pageable pageable);
}
