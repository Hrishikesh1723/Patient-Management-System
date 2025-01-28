package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.ApiRequestException;
import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Doctor;
import com.example.PatientManagementSystem.dao.DoctorDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing doctors.
 * Provides business logic for saving, retrieving, updating, and deleting appointments.
 */
@Service
public class DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    private DoctorDAO doctorDAO;

    /**
     * Saves a new doctor.
     *
     * @param doctor The doctor details to be saved.
     * @throws ServiceException if there is an error during the save operation.
     */
    public Doctor saveDoctor(Doctor doctor) {
        try {
            logger.info("Saving doctor: {}", doctor.getDoctorName());
            return doctorDAO.safeSave(doctor);
        } catch (DataAccessException ex) {
            logger.error("Error saving doctor: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save doctor", ex);
        }
    }

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id The ID of the doctor to retrieve.
     * @throws ServiceException if the doctor with the given ID is not found.
     */
    public Doctor getDoctorById(Long id) {
        try {
            logger.info("Retrieving doctor by ID: {}", id);
            return doctorDAO.safeFindById(id)
                    .orElseThrow(() -> {
                        logger.error("Doctor not found with ID: {}", id);
                        return new ServiceException("Doctor not found with ID: " + id);
                    });
        } catch (DataAccessException ex) {
            logger.error("Error retrieving doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve doctor with ID: " + id, ex);
        }
    }

    /**
     * Retrieves a paginated list of doctors with optional sorting and filtering.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (default: "doctorId,asc").
     * @param search An optional search query to filter doctors.
     * @throws ServiceException if there is an error during the retrieval process.
     */
    public Page<Doctor> getAllDoctors(int page, int size, String[] sort, String search) {
        try {
            logger.info("Fetching all doctors");
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
                return doctorDAO.findByDoctorIdContainingOrDoctorNameContaining(Long.valueOf(search), search, pageable);
            }
            return doctorDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Error fetching all doctors: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all doctors", ex);
        }
    }

    /**
     * Updates an existing doctor by their ID.
     *
     * @param id The ID of the doctor to update.
     * @param updatedDoctor  The updated doctor details.
     * @throws ServiceException if there is an error during the update process.
     */
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        try {
            logger.info("Updating doctor with ID: {}", id);
            Doctor existingDoctor = getDoctorById(id);
            existingDoctor.setDoctorName(updatedDoctor.getDoctorName());
            existingDoctor.setDepartmentId(updatedDoctor.getDepartmentId());
            existingDoctor.setDepartment(updatedDoctor.getDepartment());
            logger.info("Doctor with ID: {} updated successfully", id);
            return doctorDAO.safeSave(existingDoctor);
        } catch (DataAccessException ex) {
            logger.error("Error updating doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to update doctor with ID: " + id, ex);
        }
    }

    /**
     * Deletes a doctor by their ID.
     *
     * @param id The ID of the doctor to delete.
     * @throws ServiceException if there is an error during the delete operation.
     */
    public void deleteDoctorById(Long id) {
        try {
            logger.info("Deleting doctor with ID: {}", id);
            if (!doctorDAO.existsById(id)) {
                logger.error("Doctor with ID: {} does not exist", id);
                throw new ServiceException("Doctor with ID " + id + " does not exist");
            }
            doctorDAO.safeDeleteById(id);
            logger.info("Doctor with ID: {} deleted successfully", id);
        } catch (DataAccessException ex) {
            logger.error("Error deleting doctor with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete doctor with ID: " + id, ex);
        }
    }
}
