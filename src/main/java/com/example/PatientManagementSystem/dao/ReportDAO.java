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

@Repository
public interface ReportDAO extends JpaRepository<Report, Long> {
    Logger logger = LoggerFactory.getLogger(ReportDAO.class);

    Page<Report> findByReportIdContainingOrReportNameContaining(Long reportId, String reportName, Pageable pageable);

    default Optional<Report> safeFindById(Long id) {
        try {
            logger.info("Querying report by ID: {}", id);
            return findById(id);
        } catch (Exception ex) {
            logger.error("Error querying report with ID {}: {}", id, ex.getMessage(), ex);
            throw new DataAccessException("Failed to retrieve report with ID: " + id, ex);
        }
    }

    default Report safeSave(Report report) {
        try {
            logger.info("Saving report");
            return save(report);
        } catch (Exception ex) {
            logger.error("Error saving report: {}", ex.getMessage(), ex);
            throw new DataAccessException("Failed to save report", ex);
        }
    }

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
