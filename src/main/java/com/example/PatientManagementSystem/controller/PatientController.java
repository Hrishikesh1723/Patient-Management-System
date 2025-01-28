package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Patient;
import com.example.PatientManagementSystem.service.PatientService;
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
 * Controller class for managing patients in the patient management system.
 * Provides REST endpoints for creating, retrieving, updating, and deleting patient records.
 * Also supports pagination, sorting, and searching functionalities.
 */
@RestController
@Tag(name = "Patient Endpoints", description = "Represents individuals receiving medical care. Includes endpoints to manage patient details like name, contact information, assigned doctor, and admission date")
@RequestMapping("/api/v1/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    /**
     * Saves a new patient.
     *
     * @param patient The patient details to be saved.
     * @return A ResponseEntity containing the saved patient.
     * @throws ApiRequestException if there is an error during the save operation.
     */
    @Operation(summary = "Save New Patient", description = "Adds a new patient to the system. Requires patient details such as name, email, phone number, doctor ID, and admit date.")
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

    /**
     * Retrieves a patient by their ID.
     *
     * @param id The ID of the patient to retrieve.
     * @return A ResponseEntity containing the patient details.
     * @throws ApiRequestException if the patient with the given ID is not found.
     */
    @Operation(summary = "Fetch Patient Details by ID", description = "Retrieves the details of a specific patient using their ID.")
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

    /**
     * Retrieves a paginated list of patients with optional sorting and filtering.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (default: "patientId,asc").
     * @param search An optional search query to filter patients.
     * @return A ResponseEntity containing a paginated list of patients.
     * @throws ApiRequestException if there is an error during the retrieval process.
     */
    @Operation(summary= "Fetch Patient List", description = "Retrieves a paginated list of patients, with optional sorting.")
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

    /**
     * Updates an existing patient by their ID.
     *
     * @param id The ID of the patient to update.
     * @param updatedPatient The updated patient details.
     * @return A ResponseEntity containing the updated patient.
     * @throws ApiRequestException if there is an error during the update process.
     */
    @Operation(summary = "Edit Patient Details", description = "Updates an existing patient's details using their ID.")
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

    /**
     * Deletes a patient by their ID.
     *
     * @param id The ID of the patient to delete.
     * @return A ResponseEntity containing a success message.
     * @throws ApiRequestException if there is an error during the delete operation.
     */
    @Operation(summary = "Delete Patient by ID", description = "Removes a patient record from the system using their unique ID.")
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
