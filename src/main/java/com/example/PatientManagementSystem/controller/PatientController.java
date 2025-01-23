package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Patient;
import com.example.PatientManagementSystem.service.PatientService;
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

@RestController
@Tag(name = "Patient Endpoints", description = "Represents individuals receiving medical care. Includes endpoints to manage patient details like name, contact information, assigned doctor, and admission date")
@RequestMapping("/api/v1/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> savePatient(@Valid @RequestBody Patient patient) {
        try {
            logger.info("Received request to save patient: {}", patient.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.savePatient(patient));
        } catch (Exception ex) {
            logger.error("Error saving patient: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to save patient", ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        try {
            logger.info("Fetching patient with ID: {}", id);
            return ResponseEntity.ok(patientService.getPatientById(id));
        } catch (Exception ex) {
            logger.error("Error fetching patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Patient not found with ID: " + id, ex);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Patient>> getAllPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "patientId,asc") String[] sort,
            @RequestParam(required = false) String search
    ) {
        try {
            logger.info("Fetching all patients");
            Page<Patient> patients = patientService.getAllPatients(page, size, sort, search);
            return ResponseEntity.ok(patients);
        } catch (Exception ex) {
            logger.error("Error fetching all patients: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to fetch patients", ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient updatedPatient) {
        try {
            logger.info("Updating patient with ID: {}", id);
            return ResponseEntity.ok(patientService.updatePatient(id, updatedPatient));
        } catch (Exception ex) {
            logger.error("Error updating patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to update patient with ID: " + id, ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long id) {
        try {
            logger.info("Deleting patient with ID: {}", id);
            patientService.deletePatientById(id);
            return ResponseEntity.ok("Patient deleted successfully.");
        } catch (Exception ex) {
            logger.error("Error deleting patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to delete patient with ID: " + id, ex);
        }
    }
}
