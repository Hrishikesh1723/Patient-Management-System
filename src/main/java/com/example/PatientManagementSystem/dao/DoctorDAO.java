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

@Repository
public interface DoctorDAO extends JpaRepository<Doctor, Long> {
    Logger logger = LoggerFactory.getLogger(DoctorDAO.class);

    Page<Doctor> findByDoctorIdContainingOrDoctorNameContaining(Long doctorId, String doctorName, Pageable pageable);

    default Optional<Doctor> safeFindById(Long id) {
        try {
            logger.info("Querying doctor by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve doctor with ID: " + id, ex);
        }
    }

    default Doctor safeSave(Doctor doctor) {
        try {
            logger.info("Saving doctor");
            return save(doctor);
        } catch (Exception ex) {
            logger.error("Error saving doctor: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save doctor", ex);
        }
    }

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
