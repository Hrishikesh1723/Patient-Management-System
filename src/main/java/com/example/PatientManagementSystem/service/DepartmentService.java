package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.model.Department;
import com.example.PatientManagementSystem.dao.DepartmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDAO departmentDAO;

    public Department saveDepartment(Department department) {
        logger.info("Saving department: {}", department.getDepartmentName());
        return departmentDAO.save(department);
    }

    public Department getDepartmentById(Long id) {
        logger.info("Retrieving department by ID: {}", id);
        return departmentDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", id);
                    return new IllegalArgumentException("Department not found with ID: " + id);
                });
    }

    public List<Department> getAllDepartments() {
        logger.info("Fetching all departments");
        return departmentDAO.findAll();
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        logger.info("Updating department with ID: {}", id);
        Department existingDepartment = getDepartmentById(id);
        existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());
        logger.info("Department with ID: {} updated successfully", id);
        return departmentDAO.save(existingDepartment);
    }

    public void deleteDepartmentById(Long id) {
        logger.info("Deleting department with ID: {}", id);
        departmentDAO.deleteById(id);
        logger.info("Department with ID: {} deleted successfully", id);
    }
}

