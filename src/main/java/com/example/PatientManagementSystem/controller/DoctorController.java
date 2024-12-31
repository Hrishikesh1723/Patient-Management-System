package com.example.PatientManagementSystem.controller;

import com.example.PatientManagementSystem.model.Doctor;
import com.example.PatientManagementSystem.service.DoctorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Received request to save doctor: {}", doctor.getDoctorName());
        return ResponseEntity.ok(doctorService.saveDoctor(doctor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        logger.info("Fetching doctor with ID: {}", id);
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        logger.info("Fetching all doctors");
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor updatedDoctor) {
        logger.info("Updating doctor with ID: {}", id);
        return ResponseEntity.ok(doctorService.updateDoctor(id, updatedDoctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable Long id) {
        logger.info("Deleting doctor with ID: {}", id);
        doctorService.deleteDoctorById(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}

