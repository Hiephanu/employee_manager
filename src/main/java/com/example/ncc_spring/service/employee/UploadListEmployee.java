package com.example.ncc_spring.service.employee;

import com.example.ncc_spring.mapper.employee.EmployeeResDtoMapper;
import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.model.entity.Department;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.repository.department.DepartmentRepository;
import com.example.ncc_spring.repository.employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class UploadListEmployee {
    private final EmployeeRepository employeeRepository;
    private final  EmployeeResDtoMapper employeeResDtoMapper;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public List<EmployeeResDto> importEmployeesFromExcel(MultipartFile file) throws IOException {
        List<Employee> employees = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên, có thể thay đổi nếu có nhiều sheet

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                // Bỏ qua dòng header nếu có
                continue;
            }

            Employee employee = new Employee();
            employee.setE_name(row.getCell(0).getStringCellValue());
            System.out.println("employee name " + employee.getE_name());

            employee.setE_codeCheck(row.getCell(1).getStringCellValue());
            System.out.println("employee code " + employee.getE_codeCheck());

            employee.setE_dob(row.getCell(2).getDateCellValue());
            System.out.println("employee dob " + employee.getE_dob());

            Cell phoneCell = row.getCell(3);
            if (phoneCell != null) {
                employee.setE_phoneNumber(String.valueOf(phoneCell.getNumericCellValue()));
            }
            System.out.println("employee phone " + employee.getE_phoneNumber());

            Cell emailCell = row.getCell(4);
            if (emailCell != null) {
                employee.setE_email(emailCell.getStringCellValue());
            }
            System.out.println("employee email " + employee.getE_email());

            Cell salaryCell = row.getCell(5);
            if (salaryCell != null) {
                employee.setE_salary(Math.round(salaryCell.getNumericCellValue()));
            }
            System.out.println("employee salary " + employee.getE_salary());

            Cell addressCell = row.getCell(6);
            if (addressCell != null) {
                employee.setE_address(addressCell.getStringCellValue());
            }
            System.out.println("address" + employee.getE_address());

            Cell departmentCell = row.getCell(7);
            if (departmentCell != null) {
                employee.setDepartment(departmentRepository.findById(Math.round(departmentCell.getNumericCellValue())).get());
            }


            // Set các giá trị khác tương ứng với cấu trúc file Excel
            // Ví dụ: employee.setE_createdAt(new Date());
            employees.add(employee);
            employeeRepository.save(employee);
        }

        workbook.close();
        return employees.stream().map(employeeResDtoMapper::mapToEmployeeResDto).toList();
    }
}
