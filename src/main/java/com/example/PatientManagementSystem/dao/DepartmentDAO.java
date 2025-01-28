package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.model.Department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing Department entities.
 * Extends JpaRepository to provide basic CRUD operations.
 * Includes additional safe methods with logging and exception handling.
 */
@Repository
public interface DepartmentDAO extends JpaRepository<Department, Long> {
    Logger logger = LoggerFactory.getLogger(DepartmentDAO.class);

    /**
     * Finds a paginated list of departments by department ID.
     *
     * @param departmentId The ID of the department to search for.
     * @param pageable      Pagination and sorting details.
     * @return A page of departments matching the specified ID.
     */
    Page<Department> findByDepartmentIdContainingOrDepartmentNameContaining(Long departmentId, String departmentName, Pageable pageable);

    /**
     * Safely retrieves a department by its ID with logging and error handling.
     *
     * @param id The ID of the department to retrieve.
     * @return An Optional containing the department if found, or empty if not found.
     * @throws DataAccessException If an error occurs during the database operation.
     */
    default Optional<Department> safeFindById(Long id) {
        try {
            logger.info("Querying department by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying department with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve department with ID: " + id, ex);
        }
    }

    /**
     * Safely saves a department entity with logging and error handling.
     *
     * @param department The department entity to save.
     * @return The saved department entity.
     * @throws DataAccessException If an error occurs during the save operation.
     */
    default Department safeSave(Department department) {
        try {
            logger.info("Saving department: {}", department.getDepartmentName());
            return save(department);
        } catch (Exception ex) {
            logger.error("Error saving department: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save department", ex);
        }
    }

    /**
     * Safely deletes a department by its ID with logging and error handling.
     *
     * @param id The ID of the department to delete.
     * @throws DataAccessException If an error occurs during the delete operation.
     */
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
