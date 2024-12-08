package com.example.Patient_Management_System.controller;

import com.example.Patient_Management_System.model.Patient;
import com.example.Patient_Management_System.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public String savePatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        return "Patient added successfully!";
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable String id) {
        return patientService.getPatientById(id);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PutMapping("/{id}")
    public String updatePatient(@PathVariable String id, @RequestBody Patient patient) {
        patientService.updatePatient(id, patient);
        return "Patient updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable String id) {
        patientService.deletePatientById(id);
        return "Patient deleted successfully!";
    }
}
