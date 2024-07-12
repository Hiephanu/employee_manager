package com.example.ncc_spring.controller.employee;

import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.model.dto.EmployeeReqSaveDto;
import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.model.dto.UpdateEmployeeReqDto;
import com.example.ncc_spring.repository.employee.EmployeeRepository;
import com.example.ncc_spring.service.employee.EmployeeCrudServiceInterface;
import com.example.ncc_spring.service.employee.UploadListEmployee;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeCrudController {
    private final EmployeeCrudServiceInterface crudService;
    private final UploadListEmployee uploadListEmployee;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllEmployee(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int perPage) {
        Page<EmployeeResDto> employeesPage = crudService.getAllEmployee(page, perPage);
        return SuccessResponseHelper.createSuccessResponse(employeesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable long id) {
        return SuccessResponseHelper.createSuccessResponse(crudService.getEmployeeById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeReqSaveDto employeeReqSaveDto) {
        crudService.saveEmployee(employeeReqSaveDto);
        return  SuccessResponseHelper.createSuccessResponse("Create success");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> saveListEmployee(@RequestBody MultipartFile file) throws  IOException{
            if(file == null) {
                throw new BadRequestException("File not found");
            } else {
                    return ResponseEntity.ok(  uploadListEmployee.importEmployeesFromExcel(file));
            }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeReqDto updateEmployeeReqDto) {
        crudService.updateEmployee(updateEmployeeReqDto);
        return  SuccessResponseHelper.createSuccessResponse("Update success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable long id) {
        crudService.deleteEmployee(id);
        return  SuccessResponseHelper.createSuccessResponse("Delete success");
    }
}
