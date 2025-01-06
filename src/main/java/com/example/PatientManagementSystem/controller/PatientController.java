package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Patient;
import com.example.PatientManagementSystem.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            logger.info("Fetching all patients");
            return ResponseEntity.ok(patientService.getAllPatients());
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
