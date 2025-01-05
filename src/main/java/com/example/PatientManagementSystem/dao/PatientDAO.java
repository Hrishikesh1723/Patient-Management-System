package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.model.Patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PatientDAO extends JpaRepository<Patient, Long> {
    Logger logger = LoggerFactory.getLogger(PatientDAO.class);

    default Optional<Patient> safeFindById(Long id) {
        try {
            logger.info("Querying patient by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve patient with ID: " + id, ex);
        }
    }

    default Patient safeSave(Patient patient) {
        try {
            logger.info("Saving patient");
            return save(patient);
        } catch (Exception ex) {
            logger.error("Error saving patient: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save patient", ex);
        }
    }

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
