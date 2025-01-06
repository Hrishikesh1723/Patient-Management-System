package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Report;
import com.example.PatientManagementSystem.dao.ReportDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    private ReportDAO reportDAO;

    public Report saveReport(Report report) {
        try {
            logger.info("Saving report for patient");
            return reportDAO.safeSave(report);
        } catch (DataAccessException ex) {
            logger.error("Error saving report: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save report", ex);
        }
    }

    public Report getReportById(Long id) {
        try {
            logger.info("Retrieving report by ID: {}", id);
            return reportDAO.safeFindById(id)
                    .orElseThrow(() -> {
                        logger.error("Report not found with ID: {}", id);
                        return new ServiceException("Report not found with ID: " + id);
                    });
        } catch (DataAccessException ex) {
            logger.error("Error retrieving report with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve report with ID: " + id, ex);
        }
    }

    public Page<Report> getAllReports(int page, int size, String[] sort, String search) {
        try {
            logger.info("Fetching all reports");
            // Set up pagination and sorting
            List<Sort.Order> orders = new ArrayList<>();
            String sortField = sort[0];
            String sortDirection = sort[1];
            // Validate sort direction
            Sort.Direction direction;
            try {
                direction = Sort.Direction.fromString(sortDirection);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid sort direction: " + sortDirection + ". Expected 'asc' or 'desc'");
            }
            orders.add(new Sort.Order(direction, sortField));
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orders));

            // Return filtered results
            if (search != null && !search.isEmpty()) {
                return reportDAO.findByReportIdContainingOrReportNameContaining(Long.valueOf(search), search, pageable);
            }
            return reportDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Error fetching all reports: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all reports", ex);
        }
    }

    public void deleteReportById(Long id) {
        try {
            logger.info("Deleting report with ID: {}", id);
            if (!reportDAO.existsById(id)) {
                logger.error("Report with ID: {} does not exist", id);
                throw new ServiceException("Report with ID " + id + " does not exist");
            }
            reportDAO.safeDeleteById(id);
            logger.info("Report with ID: {} deleted successfully", id);
        } catch (DataAccessException ex) {
            logger.error("Error deleting report with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete report with ID: " + id, ex);
        }
    }
}

