package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Controller class for managing appointments.
 * Provides REST endpoints for creating, retrieving, updating, and deleting appointments.
 */
@RestController
@Tag(name = "Appointment Endpoints", description = "Represents scheduled consultations between patients and doctors. Includes endpoints to create, modify, retrieve, and delete appointments.")
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Saves a new appointment.
     *
     * @param appointment The appointment details to be saved.
     * @return A ResponseEntity containing the saved appointment.
     * @throws ApiRequestException if there is an error during the save operation.
     */
    @Operation(summary = "Save new appointment", description = "Adds a new appointment to the system.")
    @PostMapping
    public ResponseEntity<Appointment> saveAppointment(@Valid @RequestBody Appointment appointment) {
        try {
            logger.info("Saving appointment for patient");
            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.saveAppointment(appointment));
        } catch (Exception ex) {
            logger.error("Error saving appointment: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to save appointment", ex);
        }
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id The ID of the appointment to retrieve.
     * @return A ResponseEntity containing the appointment details.
     * @throws ApiRequestException if the appointment with the given ID is not found.
     */
    @Operation(summary = "Fetch appointment By Id", description = "Retrieves the details of a specific appointment using its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        try {
            logger.info("Retrieving appointment with ID: {}", id);
            return ResponseEntity.ok(appointmentService.getAppointmentById(id));
        } catch (Exception ex) {
            logger.error("Error retrieving appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Appointment not found with ID: " + id, ex);
        }
    }

    /**
     * Retrieves a paginated list of appointments with optional sorting and filtering.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (default: "appointmentId,asc").
     * @param search An optional search query to filter appointments.
     * @return A ResponseEntity containing a paginated list of appointments.
     * @throws ApiRequestException if there is an error during the retrieval process.
     */
    @Operation(summary = "Fetch appointment List", description = "Retrieves a list of all appointments.")
    @GetMapping
    public ResponseEntity<Page<Appointment>> getAllAppointments(
            @Parameter(hidden = true)
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "appointmentId,asc") String[] sort,
            @RequestParam(required = false) String search
    ) {
        try {
            logger.info("Fetching all appointments");
            Page<Appointment> appointments = appointmentService.getAllAppointments(page, size, sort, search);
            return ResponseEntity.ok(appointments);
        } catch (Exception ex) {
            logger.error("Error fetching appointments: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to fetch appointments", ex);
        }
    }

    /**
     * Updates an existing appointment by its ID.
     *
     * @param id The ID of the appointment to update.
     * @param updatedAppointment The updated appointment details.
     * @return A ResponseEntity containing the updated appointment.
     * @throws ApiRequestException if there is an error during the update process.
     */
    @Operation(summary = "Edit appointment details", description = "Updates an appointment's details using its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment updatedAppointment) {
        try {
            logger.info("Updating appointment with ID: {}", id);
            return ResponseEntity.ok(appointmentService.updateAppointment(id, updatedAppointment));
        } catch (Exception ex) {
            logger.error("Error updating appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to update appointment with ID: " + id, ex);
        }
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id The ID of the appointment to delete.
     * @return A ResponseEntity containing a success message.
     * @throws ApiRequestException if there is an error during the delete operation.
     */
    @Operation(summary = "Delete appointment by Id", description = "Removes an appointment record using its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Long id) {
        try {
            logger.info("Deleting appointment with ID: {}", id);
            appointmentService.deleteAppointmentById(id);
            return ResponseEntity.ok("Appointment deleted successfully.");
        } catch (Exception ex) {
            logger.error("Error deleting appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ApiRequestException("Failed to delete appointment with ID: " + id, ex);
        }
    }
}
