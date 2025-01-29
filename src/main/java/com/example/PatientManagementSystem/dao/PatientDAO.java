package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.model.Patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing Patient entities.
 * Extends JpaRepository to provide basic CRUD operations.
 * Includes additional safe methods with logging and exception handling.
 */
@Repository
public interface PatientDAO extends JpaRepository<Patient, Long> {
    Logger logger = LoggerFactory.getLogger(PatientDAO.class);

    /**
     * Finds a paginated list of patients by patient ID.
     *
     * @param search The ID of the patient to search for.
     * @param pageable      Pagination and sorting details.
     * @return A page of patients matching the specified ID.
     */
    @Query("SELECT p FROM Patient p WHERE " +
            "(:search IS NULL OR CAST(p.patientId AS string) LIKE %:search%) OR " +
            "(:search IS NULL OR p.name LIKE %:search%)")
    Page<Patient> findByPatientIdContainingOrNameContaining(String search, Pageable pageable);

    /**
     * Safely retrieves a patient by its ID with logging and error handling.
     *
     * @param id The ID of the patient to retrieve.
     * @return An Optional containing the patient if found, or empty if not found.
     * @throws DataAccessException If an error occurs during the database operation.
     */
    default Optional<Patient> safeFindById(Long id) {
        try {
            logger.info("Querying patient by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve patient with ID: " + id, ex);
        }
    }

    /**
     * Safely saves a patient entity with logging and error handling.
     *
     * @param patient The patient entity to save.
     * @return The saved patient entity.
     * @throws DataAccessException If an error occurs during the save operation.
     */
    default Patient safeSave(Patient patient) {
        try {
            logger.info("Saving patient");
            return save(patient);
        } catch (Exception ex) {
            logger.error("Error saving patient: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save patient", ex);
        }
    }

    /**
     * Safely deletes a patient by its ID with logging and error handling.
     *
     * @param id The ID of the patient to delete.
     * @throws DataAccessException If an error occurs during the delete operation.
     */
    default void safeDeleteById(Long id) {
        try {
            logger.info("Deleting patient with ID: {}", id);
            deleteById(id);
        } catch (Exception ex) {
            logger.error("Error deleting patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to delete patient with ID: " + id, ex);
        }
    }
}
