package com.example.ncc_spring.controller.department;

import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.model.entity.Department;
import com.example.ncc_spring.repository.department.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("departments")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    @PostMapping("")
    public ResponseEntity<?> saveDepartment(Department department) {
        departmentRepository.save(department);
        return SuccessResponseHelper.createSuccessResponse("Save success");
    }
}
