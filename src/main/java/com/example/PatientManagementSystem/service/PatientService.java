package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Patient;
import com.example.PatientManagementSystem.dao.PatientDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service layer for managing patients.
 * Provides business logic for saving, retrieving, updating, and deleting appointments.
 */
@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientDAO patientDAO;

    /**
     * Saves a new patient.
     *
     * @param patient The patient details to be saved.
     * @throws ServiceException if there is an error during the save operation.
     */
    public Patient savePatient(Patient patient) {
        try {
            logger.info("Saving patient: {}", patient.getName());
            return patientDAO.safeSave(patient);
        } catch (DataAccessException ex) {
            logger.error("Error saving patient: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save patient", ex);
        }
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param id The ID of the patient to retrieve.
     * @throws ServiceException if the patient with the given ID is not found.
     */
    public Patient getPatientById(Long id) {
        try {
            logger.info("Retrieving patient by ID: {}", id);
            return patientDAO.safeFindById(id)
                    .orElseThrow(() -> {
                        logger.error("Patient not found with ID: {}", id);
                        return new ServiceException("Patient not found with ID: " + id);
                    });
        } catch (DataAccessException ex) {
            logger.error("Error retrieving patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve patient with ID: " + id, ex);
        }
    }

    /**
     * Retrieves a paginated list of patients with optional sorting and filtering.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (default: "patientId,asc").
     * @param search An optional search query to filter patients.
     * @throws ServiceException if there is an error during the retrieval process.
     */
    public Page<Patient> getAllPatients(int page, int size, String[] sort, String search) {
        try {
            logger.info("Sort options received: {}", Arrays.toString(sort));
            logger.info("Fetching all patients");
            // Set up pagination and sorting
            List<Sort.Order> orders = new ArrayList<>();
            String sortField = sort[0];
            String sortDirection = sort[1];
            // Validate sort direction
            Sort.Direction direction;
            try {
                direction = Sort.Direction.fromString(sortDirection);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid sort direction: " + sortDirection + ". Expected 'asc' or 'desc'");
            }
            orders.add(new Sort.Order(direction, sortField));
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orders));

            // Return filtered results
            if (search != null && !search.isEmpty()) {
                return patientDAO.findByPatientIdContainingOrNameContaining(Long.valueOf(search), search, pageable);
            }
            return patientDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Error fetching all patients: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all patients", ex);
        }
    }

    /**
     * Updates an existing patient by their ID.
     *
     * @param id The ID of the patient to update.
     * @param updatedPatient The updated patient details.
     * @throws ServiceException if there is an error during the update process.
     */
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
            return patientDAO.safeSave(existingPatient);
        } catch (DataAccessException ex) {
            logger.error("Error updating patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to update patient with ID: " + id, ex);
        }
    }

    /**
     * Deletes a patient by their ID.
     *
     * @param id The ID of the patient to delete.
     * @throws ServiceException if there is an error during the delete operation.
     */
    public void deletePatientById(Long id) {
        try {
            logger.info("Deleting patient with ID: {}", id);
            if (!patientDAO.existsById(id)) {
                logger.error("Patient with ID: {} does not exist", id);
                throw new ServiceException("Patient with ID " + id + " does not exist");
            }
            patientDAO.safeDeleteById(id);
            logger.info("Patient with ID: {} deleted successfully", id);
        } catch (DataAccessException ex) {
            logger.error("Error deleting patient with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete patient with ID: " + id, ex);
        }
    }
}