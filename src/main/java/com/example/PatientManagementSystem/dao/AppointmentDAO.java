package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing Appointment entities.
 * Extends JpaRepository to provide basic CRUD operations.
 * Includes additional safe methods with logging and exception handling.
 */
public interface AppointmentDAO extends JpaRepository<Appointment, Long>{
    Logger logger = LoggerFactory.getLogger(AppointmentDAO.class);

    /**
     * Finds a paginated list of appointments by appointment ID.
     *
     * @param appointmentId The ID of the appointment to search for.
     * @param pageable      Pagination and sorting details.
     * @return A page of appointments matching the specified ID.
     */
    Page<Appointment> findByAppointmentId(Long appointmentId, Pageable pageable);

    /**
     * Safely retrieves an appointment by its ID with logging and error handling.
     *
     * @param id The ID of the appointment to retrieve.
     * @return An Optional containing the appointment if found, or empty if not found.
     * @throws DataAccessException If an error occurs during the database operation.
     */
    default Optional<Appointment> safeFindById(Long id) {
        try {
            logger.info("Querying appointment by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve appointment with ID: " + id, ex);
        }
    }

    /**
     * Safely saves an appointment entity with logging and error handling.
     *
     * @param appointment The appointment entity to save.
     * @return The saved appointment entity.
     * @throws DataAccessException If an error occurs during the save operation.
     */
    default Appointment safeSave(Appointment appointment) {
        try {
            logger.info("Saving appointment for patient with ID: {}", appointment.getPatientId());
            return save(appointment);
        } catch (Exception ex) {
            logger.error("Error saving appointment: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save appointment", ex);
        }
    }

    /**
     * Safely deletes an appointment by its ID with logging and error handling.
     *
     * @param id The ID of the appointment to delete.
     * @throws DataAccessException If an error occurs during the delete operation.
     */
    default void safeDeleteById(Long id) {
        try {
            logger.info("Deleting appointment with ID: {}", id);
            deleteById(id);
        } catch (Exception ex) {
            logger.error("Error deleting appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to delete appointment with ID: " + id, ex);
        }
    }
}
