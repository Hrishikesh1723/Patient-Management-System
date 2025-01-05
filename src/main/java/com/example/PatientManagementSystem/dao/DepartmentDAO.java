package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentDAO extends JpaRepository<Department, Long> {
    Logger logger = LoggerFactory.getLogger(DepartmentDAO.class);

    default Optional<Department> safeFindById(Long id) {
        try {
            logger.info("Querying department by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying department with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve department with ID: " + id, ex);
        }
    }

    default Department safeSave(Department department) {
        try {
            logger.info("Saving department: {}", department.getDepartmentName());
            return save(department);
        } catch (Exception ex) {
            logger.error("Error saving department: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save department", ex);
        }
    }

    default void safeDeleteById(Long id) {
        try {
            logger.info("Deleting department with ID: {}", id);
            deleteById(id);
        } catch (Exception ex) {
            logger.error("Error deleting department with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to delete department with ID: " + id, ex);
        }
    }
}
