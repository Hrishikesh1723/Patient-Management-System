package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.model.Doctor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing Doctor entities.
 * Extends JpaRepository to provide basic CRUD operations.
 * Includes additional safe methods with logging and exception handling.
 */
@Repository
public interface DoctorDAO extends JpaRepository<Doctor, Long> {
    Logger logger = LoggerFactory.getLogger(DoctorDAO.class);

    /**
     * Finds a paginated list of doctors by doctor ID.
     *
     * @param doctorId The ID of the doctor to search for.
     * @param pageable      Pagination and sorting details.
     * @return A page of doctors matching the specified ID.
     */
    Page<Doctor> findByDoctorIdContainingOrDoctorNameContaining(Long doctorId, String doctorName, Pageable pageable);

    /**
     * Safely retrieves a doctor by its ID with logging and error handling.
     *
     * @param id The ID of the doctor to retrieve.
     * @return An Optional containing the doctor if found, or empty if not found.
     * @throws DataAccessException If an error occurs during the database operation.
     */
    default Optional<Doctor> safeFindById(Long id) {
        try {
            logger.info("Querying doctor by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve doctor with ID: " + id, ex);
        }
    }

    /**
     * Safely saves a doctor entity with logging and error handling.
     *
     * @param doctor The doctor entity to save.
     * @return The saved doctor entity.
     * @throws DataAccessException If an error occurs during the save operation.
     */
    default Doctor safeSave(Doctor doctor) {
        try {
            logger.info("Saving doctor");
            return save(doctor);
        } catch (Exception ex) {
            logger.error("Error saving doctor: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save doctor", ex);
        }
    }

    /**
     * Safely deletes a doctor by its ID with logging and error handling.
     *
     * @param id The ID of the doctor to delete.
     * @throws DataAccessException If an error occurs during the delete operation.
     */
    default void safeDeleteById(Long id) {
        try {
            logger.info("Deleting doctor with ID: {}", id);
            deleteById(id);
        } catch (Exception ex) {
            logger.error("Error deleting doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to delete doctor with ID: " + id, ex);
        }
    }
}
