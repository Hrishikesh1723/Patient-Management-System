package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Report;
import com.example.PatientManagementSystem.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing medical reports linked to patients.
 * Provides endpoints for creating, retrieving, and deleting medical reports.
 */
@RestController
@Tag(name = "Report Endpoints", description = "Represents medical reports linked to patients, such as diagnostic tests or health summaries. Endpoints allow adding, retrieving, and deleting reports")
@RequestMapping("/api/v1/reports")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    /**
     * Saves a new report.
     *
     * @param report The report details to be saved.
     * @return A ResponseEntity containing the saved report.
     * @throws ApiRequestException if there is an error during the save operation.
     */
    @Operation(summary = "Save new report", description = "Adds a new report to the system")
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

    /**
     * Retrieves a report by its ID.
     *
     * @param id The ID of the report to retrieve.
     * @return A ResponseEntity containing the report details.
     * @throws ApiRequestException if the report with the given ID is not found.
     */
    @Operation(summary = "Fetch Report By ID", description = "Retrieves the details of a specific report using its ID.")
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

    /**
     * Retrieves a paginated list of reports with optional sorting and filtering.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (default: "reportId,asc").
     * @param search An optional search query to filter reports.
     * @return A ResponseEntity containing a paginated list of reports.
     * @throws ApiRequestException if there is an error during the retrieval process.
     */
    @Operation(summary = "Fetch report List", description = "Retrieves a list of all reports.")
    @GetMapping
    public ResponseEntity<Page<Report>> getAllReports(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "reportId,asc") String[] sort,
            @RequestParam(required = false) String search
    ) {
        try {
            logger.info("Fetching all reports");
            Page<Report> reports = reportService.getAllReports(page, size, sort, search);
            return ResponseEntity.ok(reports);
        } catch (Exception ex) {
            logger.error("Error fetching all reports: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to fetch reports", ex);
        }
    }

    /**
     * Deletes a report by its ID.
     *
     * @param id The ID of the report to delete.
     * @return A ResponseEntity containing a success message.
     * @throws ApiRequestException if there is an error during the delete operation.
     */
    @Operation(summary = "Delete report by ID", description = "Removes a report record using its unique ID.")
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
