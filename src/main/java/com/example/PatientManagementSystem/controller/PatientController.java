package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.model.Patient;
import com.example.PatientManagementSystem.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Received request to save patient: {}", patient.getName());
        return ResponseEntity.ok(patientService.savePatient(patient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient with ID: {}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        logger.info("Fetching all patients");
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient updatedPatient) {
        logger.info("Updating patient with ID: {}", id);
        return ResponseEntity.ok(patientService.updatePatient(id, updatedPatient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long id) {
        logger.info("Deleting patient with ID: {}", id);
        patientService.deletePatientById(id);
        return ResponseEntity.ok("Patient deleted successfully.");
    }
}