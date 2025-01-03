package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.model.Report;
import com.example.PatientManagementSystem.dao.ReportDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    private ReportDAO reportDAO;

    public Report saveReport(Report report) {
        logger.info("Saving report for patient");
        return reportDAO.save(report);
    }

    public Report getReportById(Long id) {
        logger.info("Retrieving report by ID: {}", id);
        return reportDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Report not found with ID: {}", id);
                    return new IllegalArgumentException("Report not found with ID: " + id);
                });
    }

    public List<Report> getAllReports() {
        logger.info("Fetching all reports");
        return reportDAO.findAll();
    }

    public void deleteReportById(Long id) {
        logger.info("Deleting report with ID: {}", id);
        reportDAO.deleteById(id);
        logger.info("Report with ID: {} deleted successfully", id);
    }
}

