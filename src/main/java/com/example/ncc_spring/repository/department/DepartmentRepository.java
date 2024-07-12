package com.example.ncc_spring.repository.department;

import com.example.ncc_spring.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department ,Long> {
}
