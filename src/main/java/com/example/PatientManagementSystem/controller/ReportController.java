package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Report;
import com.example.PatientManagementSystem.service.ReportService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> saveReport(@Valid @RequestBody Report report) {
        try {
            logger.info("Received request to save report for patient with ID: {}", report.getPatientId());
            return ResponseEntity.status(HttpStatus.CREATED).body(reportService.saveReport(report));
        } catch (Exception ex) {
            logger.error("Error saving report for patient with ID: {}", report.getPatientId(), ex);
            throw new ApiRequestException("Failed to save report for patient with ID: " + report.getPatientId(), ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        try {
            logger.info("Fetching report with ID: {}", id);
            return ResponseEntity.ok(reportService.getReportById(id));
        } catch (Exception ex) {
            logger.error("Error fetching report with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Report not found with ID: " + id, ex);
        }
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        try {
            logger.info("Fetching all reports");
            return ResponseEntity.ok(reportService.getAllReports());
        } catch (Exception ex) {
            logger.error("Error fetching all reports: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to fetch reports", ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReportById(@PathVariable Long id) {
        try {
            logger.info("Deleting report with ID: {}", id);
            reportService.deleteReportById(id);
            return ResponseEntity.ok("Report deleted successfully.");
        } catch (Exception ex) {
            logger.error("Error deleting report with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to delete report with ID: " + id, ex);
        }
    }
}
