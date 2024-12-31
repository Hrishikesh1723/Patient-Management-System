package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.service.AppointmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Received request to save appointment for patient ID: {}", appointment.getPatient().getId());
        return ResponseEntity.ok(appointmentService.saveAppointment(appointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        logger.info("Fetching appointment with ID: {}", id);
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        logger.info("Fetching all appointments");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment updatedAppointment) {
        logger.info("Updating appointment with ID: {}", id);
        return ResponseEntity.ok(appointmentService.updateAppointment(id, updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Long id) {
        logger.info("Deleting appointment with ID: {}", id);
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.ok("Appointment deleted successfully.");
    }
}

