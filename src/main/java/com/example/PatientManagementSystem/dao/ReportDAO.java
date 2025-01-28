package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.model.Report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing Report entities.
 * Extends JpaRepository to provide basic CRUD operations.
 * Includes additional safe methods with logging and exception handling.
 */
@Repository
public interface ReportDAO extends JpaRepository<Report, Long> {
    Logger logger = LoggerFactory.getLogger(ReportDAO.class);

    /**
     * Finds a paginated list of reports by report ID.
     *
     * @param reportId The ID of the report to search for.
     * @param pageable      Pagination and sorting details.
     * @return A page of reports matching the specified ID.
     */
    Page<Report> findByReportIdContainingOrReportNameContaining(Long reportId, String reportName, Pageable pageable);

    /**
     * Safely retrieves a report by its ID with logging and error handling.
     *
     * @param id The ID of the report to retrieve.
     * @return An Optional containing the report if found, or empty if not found.
     * @throws DataAccessException If an error occurs during the database operation.
     */
    default Optional<Report> safeFindById(Long id) {
        try {
            logger.info("Querying report by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying report with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve report with ID: " + id, ex);
        }
    }

    /**
     * Safely saves a report entity with logging and error handling.
     *
     * @param report The report entity to save.
     * @return The saved report entity.
     * @throws DataAccessException If an error occurs during the save operation.
     */
    default Report safeSave(Report report) {
        try {
            logger.info("Saving report");
            return save(report);
        } catch (Exception ex) {
            logger.error("Error saving report: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save report", ex);
        }
    }

    /**
     * Safely deletes a report by its ID with logging and error handling.
     *
     * @param id The ID of the report to delete.
     * @throws DataAccessException If an error occurs during the delete operation.
     */
    default void safeDeleteById(Long id) {
        try {
            logger.info("Deleting report with ID: {}", id);
            deleteById(id);
        } catch (Exception ex) {
            logger.error("Error deleting report with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to delete report with ID: " + id, ex);
        }
    }
}
