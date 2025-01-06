package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentDAO extends JpaRepository<Appointment, Long>{
    Logger logger = LoggerFactory.getLogger(AppointmentDAO.class);

    Page<Appointment> findByAppointmentId(Long appointmentId, Pageable pageable);

    default Optional<Appointment> safeFindById(Long id) {
        try {
            logger.info("Querying appointment by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve appointment with ID: " + id, ex);
        }
    }

    default Appointment safeSave(Appointment appointment) {
        try {
            logger.info("Saving appointment for patient with ID: {}", appointment.getPatientId());
            return save(appointment);
        } catch (Exception ex) {
            logger.error("Error saving appointment: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save appointment", ex);
        }
    }

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
