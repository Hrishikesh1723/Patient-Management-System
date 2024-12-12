package com.example.Patient_Management_System.service;

import com.example.Patient_Management_System.model.Patient;
import com.example.Patient_Management_System.dao.PatientDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientDAO patientDAO;

    public Patient savePatient(Patient patient) {
        logger.info("Saving patient: {}", patient.getName());
        return patientDAO.save(patient);
    }

    public Patient getPatientById(Long id) {
        logger.info("Retrieving patient by ID: {}", id);
        return patientDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found with ID: {}", id);
                    return new IllegalArgumentException("Patient not found with ID: " + id);
                });
    }

    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        return patientDAO.findAll();
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        logger.info("Updating patient with ID: {}", id);
        Patient existingPatient = getPatientById(id);
        existingPatient.setName(updatedPatient.getName());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setPhone(updatedPatient.getPhone());
        existingPatient.setReportList(updatedPatient.getReportList());
        existingPatient.setAdmitDate(updatedPatient.getAdmitDate());
        logger.info("Patient with ID: {} updated successfully", id);
        return existingPatient;
    }

    public void deletePatientById(Long id) {
        logger.info("Deleting patient with ID: {}", id);
        patientDAO.deleteById(id);
        logger.info("Patient with ID: {} deleted successfully", id);
    }
}