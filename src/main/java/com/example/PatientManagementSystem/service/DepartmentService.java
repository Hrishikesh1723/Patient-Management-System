package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Department;
import com.example.PatientManagementSystem.dao.DepartmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDAO departmentDAO;

    public Department saveDepartment(Department department) {
        try {
            logger.info("Saving department: {}", department.getDepartmentName());
            return departmentDAO.safeSave(department);
        } catch (DataAccessException ex) {
            logger.error("Error saving department: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save department", ex);
        }
    }

    public Department getDepartmentById(Long id) {
        try {
            logger.info("Retrieving department by ID: {}", id);
            return departmentDAO.safeFindById(id)
                    .orElseThrow(() -> {
                        logger.error("Department not found with ID: {}", id);
                        return new ServiceException("Department not found with ID: " + id);
                    });
        } catch (DataAccessException ex) {
            logger.error("Error retrieving department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve department with ID: " + id, ex);
        }
    }

    public Page<Department> getAllDepartments(int page, int size, String[] sort, String search) {
        try {
            logger.info("Fetching all departments");
            // Set up pagination and sorting
            List<Sort.Order> orders = new ArrayList<>();
            String sortField = sort[0];
            String sortDirection = sort[1];
            // Validate sort direction
            Sort.Direction direction;
            try {
                direction = Sort.Direction.fromString(sortDirection);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid sort direction: " + sortDirection + ". Expected 'asc' or 'desc'");
            }
            orders.add(new Sort.Order(direction, sortField));
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orders));

            // Return filtered results
            if (search != null && !search.isEmpty()) {
                return departmentDAO.findByDepartmentIdContainingOrDepartmentNameContaining(Long.valueOf(search), search, pageable);
            }
            return departmentDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Error fetching all departments: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all departments", ex);
        }
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        try {
            logger.info("Updating department with ID: {}", id);
            Department existingDepartment = getDepartmentById(id);
            existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());
            logger.info("Department with ID: {} updated successfully", id);
            return departmentDAO.safeSave(existingDepartment);
        } catch (DataAccessException ex) {
            logger.error("Error updating department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to update department with ID: " + id, ex);
        }
    }

    public void deleteDepartmentById(Long id) {
        try {
            logger.info("Deleting department with ID: {}", id);
            if (!departmentDAO.existsById(id)) {
                logger.error("Department with ID: {} does not exist", id);
                throw new ServiceException("Department with ID " + id + " does not exist");
            }
            departmentDAO.safeDeleteById(id);
            logger.info("Department with ID: {} deleted successfully", id);
        } catch (DataAccessException ex) {
            logger.error("Error deleting department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete department with ID: " + id, ex);
        }
    }
}

