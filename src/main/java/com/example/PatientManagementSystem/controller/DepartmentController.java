package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Department;
import com.example.PatientManagementSystem.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing departments.
 * Provides REST endpoints for creating, retrieving, updating, and deleting departments.
 */
@RestController
@Tag(name = "Department Endpoints", description = "Represents hospital departments (e.g., Cardiology, Neurology) with endpoints to create, update, delete, and fetch department records")
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * Saves a new department.
     *
     * @param department The department details to be saved.
     * @return A ResponseEntity containing the saved department.
     * @throws ApiRequestException if there is an error during the save operation.
     */
    @Operation(summary = "Save new department", description = "Adds a new department to the system.")
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

    /**
     * Retrieves a department by its ID.
     *
     * @param id The ID of the department to retrieve.
     * @return A ResponseEntity containing the department details.
     * @throws ApiRequestException if the department with the given ID is not found.
     */
    @Operation(summary = "Fetch department by ID", description = "Retrieves the details of a specific department using its ID.")
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

    /**
     * Retrieves a paginated list of departments with optional sorting and filtering.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (default: "departmentId,asc").
     * @param search An optional search query to filter departments.
     * @return A ResponseEntity containing a paginated list of departments.
     * @throws ApiRequestException if there is an error during the retrieval process.
     */
    @Operation(summary = "Fetch department List", description = "Retrieves a list of all departments.")
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

    /**
     * Updates an existing department by its ID.
     *
     * @param id The ID of the department to update.
     * @param updatedDepartment The updated department details.
     * @return A ResponseEntity containing the updated department.
     * @throws ApiRequestException if there is an error during the update process.
     */
    @Operation(summary = "Edit department details", description = "Updates an existing department's details using its ID.")
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

    /**
     * Deletes a department by its ID.
     *
     * @param id The ID of the department to delete.
     * @return A ResponseEntity containing a success message.
     * @throws ApiRequestException if there is an error during the delete operation.
     */
    @Operation(summary = "Delete department", description = "Removes a department record using its unique ID.")
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
