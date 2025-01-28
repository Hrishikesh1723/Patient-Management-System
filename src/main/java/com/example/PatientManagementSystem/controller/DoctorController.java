package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Doctor;
import com.example.PatientManagementSystem.service.DoctorService;
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
 * Controller class for managing doctors in the patient management system.
 * Provides REST endpoints for creating, retrieving, updating, and deleting doctor records.
 * Also supports pagination, sorting, and searching functionalities.
 */
@RestController
@Tag(name = "Doctor Endpoints", description = "Represents medical professionals associated with departments. Includes endpoints to manage doctor details and their associations with departments.")
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;

    /**
     * Saves a new doctor.
     *
     * @param doctor The doctor details to be saved.
     * @return A ResponseEntity containing the saved doctor.
     * @throws ApiRequestException if there is an error during the save operation.
     */
    @Operation(summary = "Save new Doctor", description = "Adds a new doctor to the system.")
    @PostMapping
    public ResponseEntity<Doctor> saveDoctor(@Valid @RequestBody Doctor doctor) {
        try {
            logger.info("Received request to save doctor: {}", doctor.getDoctorName());
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.saveDoctor(doctor));
        } catch (Exception ex) {
            logger.error("Error saving doctor: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to save doctor", ex);
        }
    }

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id The ID of the doctor to retrieve.
     * @return A ResponseEntity containing the doctor details.
     * @throws ApiRequestException if the doctor with the given ID is not found.
     */
    @Operation(summary = "Fetch Doctor details by Id", description = "Retrieves the details of a specific doctor using their ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        try {
            logger.info("Fetching doctor with ID: {}", id);
            return ResponseEntity.ok(doctorService.getDoctorById(id));
        } catch (Exception ex) {
            logger.error("Error fetching doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Doctor not found with ID: " + id, ex);
        }
    }

    /**
     * Retrieves a paginated list of doctors with optional sorting and filtering.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (default: "doctorId,asc").
     * @param search An optional search query to filter doctors.
     * @return A ResponseEntity containing a paginated list of doctors.
     * @throws ApiRequestException if there is an error during the retrieval process.
     */
    @Operation(summary = "Fetch Doctor List", description = "Retrieves a list of all doctors.")
    @GetMapping
    public ResponseEntity<Page<Doctor>> getAllDoctors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "doctorId,asc") String[] sort,
            @RequestParam(required = false) String search
    ) {
        try {
            logger.info("Fetching all doctors");
            Page<Doctor> doctors = doctorService.getAllDoctors(page, size, sort, search);
            return ResponseEntity.ok(doctors);
        } catch (Exception ex) {
            logger.error("Error fetching doctors: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to fetch doctors", ex);
        }
    }

    /**
     * Updates an existing doctor by their ID.
     *
     * @param id The ID of the doctor to update.
     * @param updatedDoctor  The updated doctor details.
     * @return A ResponseEntity containing the updated doctor.
     * @throws ApiRequestException if there is an error during the update process.
     */
    @Operation(summary = "Edit doctor details", description = "Updates a doctor's details using their ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor updatedDoctor) {
        try {
            logger.info("Updating doctor with ID: {}", id);
            return ResponseEntity.ok(doctorService.updateDoctor(id, updatedDoctor));
        } catch (Exception ex) {
            logger.error("Error updating doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to update doctor with ID: " + id, ex);
        }
    }

    /**
     * Deletes a doctor by their ID.
     *
     * @param id The ID of the doctor to delete.
     * @return A ResponseEntity containing a success message.
     * @throws ApiRequestException if there is an error during the delete operation.
     */
    @Operation(summary = "Delete Doctor by Id", description = "Removes a doctor record using their unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable Long id) {
        try {
            logger.info("Deleting doctor with ID: {}", id);
            doctorService.deleteDoctorById(id);
            return ResponseEntity.ok("Doctor deleted successfully.");
        } catch (Exception ex) {
            logger.error("Error deleting doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to delete doctor with ID: " + id, ex);
        }
    }
}
