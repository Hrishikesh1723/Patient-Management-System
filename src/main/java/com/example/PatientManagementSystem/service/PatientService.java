package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Patient;
import com.example.PatientManagementSystem.dao.PatientDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientDAO patientDAO;

    public Patient savePatient(Patient patient) {
        try {
            logger.info("Saving patient: {}", patient.getName());
            return patientDAO.save(patient);
        } catch (Exception ex) {
            logger.error("Error saving patient: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save patient", ex);
        }
    }

    public Patient getPatientById(Long id) {
        try {
            logger.info("Retrieving patient by ID: {}", id);
            return patientDAO.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Patient not found with ID: {}", id);
                        return new ServiceException("Patient not found with ID: " + id);
                    });
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error retrieving patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve patient with ID: " + id, ex);
        }
    }

    public List<Patient> getAllPatients() {
        try {
            logger.info("Fetching all patients");
            return patientDAO.findAll();
        } catch (Exception ex) {
            logger.error("Error fetching all patients: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all patients", ex);
        }
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        try {
            logger.info("Updating patient with ID: {}", id);
            Patient existingPatient = getPatientById(id);
            existingPatient.setName(updatedPatient.getName());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setPhone(updatedPatient.getPhone());
            existingPatient.setDoctor(updatedPatient.getDoctor());
            existingPatient.setDoctorId(updatedPatient.getDoctorId());
            existingPatient.setAdmitDate(updatedPatient.getAdmitDate());
            logger.info("Patient with ID: {} updated successfully", id);
            return patientDAO.save(existingPatient);
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error updating patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to update patient with ID: " + id, ex);
        }
    }

    public void deletePatientById(Long id) {
        try {
            logger.info("Deleting patient with ID: {}", id);
            if (!patientDAO.existsById(id)) {
                logger.error("Patient with ID: {} does not exist", id);
                throw new ServiceException("Patient with ID " + id + " does not exist");
            }
            patientDAO.deleteById(id);
            logger.info("Patient with ID: {} deleted successfully", id);
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error deleting patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete patient with ID: " + id, ex);
        }
    }
}