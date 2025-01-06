package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.service.AppointmentService;
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
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

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

    @GetMapping
    public ResponseEntity<Page<Appointment>> getAllAppointments(
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
