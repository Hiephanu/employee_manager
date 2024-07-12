package com.example.ncc_spring.controller.employee;

import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.service.employee.EmployeeFilterAndSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeSearchAndFilterController {
    private final EmployeeFilterAndSearchService employeeFilterAndSearchService;

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String keyword,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int perPage) {
        return  SuccessResponseHelper.createSuccessResponse(employeeFilterAndSearchService.findByName(keyword, page, perPage));
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<?> getEmployeeByDepartmentId(@PathVariable long id,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int perPage) {
        return  SuccessResponseHelper.createSuccessResponse(employeeFilterAndSearchService.getEmployeeByDepartment(id, page, perPage));
    }
}
