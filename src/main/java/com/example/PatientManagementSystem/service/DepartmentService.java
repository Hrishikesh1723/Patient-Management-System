package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.ServiceException;
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
        try {
            logger.info("Saving department: {}", department.getDepartmentName());
            return departmentDAO.save(department);
        } catch (Exception ex) {
            logger.error("Error saving department: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save department", ex);
        }
    }

    public Department getDepartmentById(Long id) {
        try {
            logger.info("Retrieving department by ID: {}", id);
            return departmentDAO.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Department not found with ID: {}", id);
                        return new ServiceException("Department not found with ID: " + id);
                    });
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error retrieving department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve department with ID: " + id, ex);
        }
    }

    public List<Department> getAllDepartments() {
        try {
            logger.info("Fetching all departments");
            return departmentDAO.findAll();
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
            return departmentDAO.save(existingDepartment);
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
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
            departmentDAO.deleteById(id);
            logger.info("Department with ID: {} deleted successfully", id);
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error deleting department with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete department with ID: " + id, ex);
        }
    }
}

