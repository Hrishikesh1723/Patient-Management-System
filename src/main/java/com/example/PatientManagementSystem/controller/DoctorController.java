package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.model.Doctor;
import com.example.PatientManagementSystem.service.DoctorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;

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

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        try {
            logger.info("Fetching all doctors");
            return ResponseEntity.ok(doctorService.getAllDoctors());
        } catch (Exception ex) {
            logger.error("Error fetching doctors: {}", ex.getMessage(), ex);
            throw new ApiRequestException("Failed to fetch doctors", ex);
        }
    }

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
