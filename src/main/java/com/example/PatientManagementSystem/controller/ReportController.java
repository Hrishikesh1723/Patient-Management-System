package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.model.Report;
import com.example.PatientManagementSystem.service.ReportService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Received request to save report for patient ID: {}", report.getPatient().getId());
        return ResponseEntity.ok(reportService.saveReport(report));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        logger.info("Fetching report with ID: {}", id);
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        logger.info("Fetching all reports");
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReportById(@PathVariable Long id) {
        logger.info("Deleting report with ID: {}", id);
        reportService.deleteReportById(id);
        return ResponseEntity.ok("Report deleted successfully.");
    }
}

