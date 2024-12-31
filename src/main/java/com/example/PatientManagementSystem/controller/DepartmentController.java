package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.model.Department;
import com.example.PatientManagementSystem.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Received request to save department: {}", department.getDepartmentName());
        return ResponseEntity.ok(departmentService.saveDepartment(department));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        logger.info("Fetching department with ID: {}", id);
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        logger.info("Fetching all departments");
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department updatedDepartment) {
        logger.info("Updating department with ID: {}", id);
        return ResponseEntity.ok(departmentService.updateDepartment(id, updatedDepartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable Long id) {
        logger.info("Deleting department with ID: {}", id);
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.ok("Department deleted successfully.");
    }
}

