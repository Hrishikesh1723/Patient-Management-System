package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Department;
import com.example.PatientManagementSystem.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> saveDepartment(@Valid @RequestBody Department department) {
        try {
            logger.info("Received request to save department: {}", department.getDepartmentName());
            return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.saveDepartment(department));
        } catch (Exception ex) {
            logger.error("Error saving department: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to save department", ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        try {
            logger.info("Fetching department with ID: {}", id);
            return ResponseEntity.ok(departmentService.getDepartmentById(id));
        } catch (Exception ex) {
            logger.error("Error fetching department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Department not found with ID: " + id, ex);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Department>> getAllDepartments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "departmentId,asc") String[] sort,
            @RequestParam(required = false) String search
    ) {
        try {
            logger.info("Fetching all departments");
            Page<Department> departments = departmentService.getAllDepartments(page, size, sort, search);
            return ResponseEntity.ok(departments);
        } catch (Exception ex) {
            logger.error("Error fetching departments: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to fetch departments", ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department updatedDepartment) {
        try {
            logger.info("Updating department with ID: {}", id);
            return ResponseEntity.ok(departmentService.updateDepartment(id, updatedDepartment));
        } catch (Exception ex) {
            logger.error("Error updating department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to update department with ID: " + id, ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable Long id) {
        try {
            logger.info("Deleting department with ID: {}", id);
            departmentService.deleteDepartmentById(id);
            return ResponseEntity.ok("Department deleted successfully.");
        } catch (Exception ex) {
            logger.error("Error deleting department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to delete department with ID: " + id, ex);
        }
    }
}
