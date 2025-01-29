package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.dao.AppointmentDAO;
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
 * Service layer for managing appointments.
 * Provides business logic for saving, retrieving, updating, and deleting appointments.
 */
@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentDAO appointmentDAO;

    /**
     * Saves a new appointment.
     *
     * @param appointment The appointment details to be saved.
     * @return The saved appointment.
     * @throws ServiceException if there is an error during the save operation.
     */
    public Appointment saveAppointment(Appointment appointment) {
        try {
            logger.info("Saving appointment for patient");
            return appointmentDAO.safeSave(appointment);
        } catch (DataAccessException ex) {
            logger.error("Error saving appointment: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save appointment", ex);
        }
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id The ID of the appointment to retrieve.
     * @return The retrieved appointment.
     * @throws ServiceException if the appointment is not found or there is a retrieval error.
     */
    public Appointment getAppointmentById(Long id) {
        try {
            logger.info("Retrieving appointment by ID: {}", id);
            return appointmentDAO.safeFindById(id)
                    .orElseThrow(() -> {
                        logger.error("Appointment not found with ID: {}", id);
                        return new ServiceException("Appointment not found with ID: " + id);
                    });
        } catch (DataAccessException ex) {
            logger.error("Error retrieving appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve appointment with ID: " + id, ex);
        }
    }

    /**
     * Retrieves all appointments with pagination, sorting, and optional search.
     *
     * @param page   The page number to retrieve (default: 1).
     * @param size   The number of records per page (default: 20).
     * @param sort   The sort criteria in the format "field,order" (e.g., "appointmentDate,asc").
     * @param search Optional search query to filter appointments by ID.
     * @return A paginated list of appointments.
     * @throws ServiceException if there is an error during the retrieval process.
     */
    public Page<Appointment> getAllAppointments(int page, int size, String[] sort, String search) {
        try {
            logger.info("Fetching all appointments");
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
                return appointmentDAO.findByAppointmentId(search, pageable);
            }
            return appointmentDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Error fetching all appointments: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all appointments", ex);
        }
    }

    /**
     * Updates an existing appointment by ID.
     *
     * @param id The ID of the appointment to update.
     * @param updatedAppointment The updated appointment details.
     * @return The updated appointment.
     * @throws ServiceException if the appointment is not found or there is an update error.
     */
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        try {
            logger.info("Updating appointment with ID: {}", id);
            Appointment existingAppointment = getAppointmentById(id);
            existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
            existingAppointment.setDoctor(updatedAppointment.getDoctor());
            existingAppointment.setPatient(updatedAppointment.getPatient());
            existingAppointment.setDoctorId(updatedAppointment.getDoctorId());
            existingAppointment.setPatientId(updatedAppointment.getPatientId());
            logger.info("Appointment with ID: {} updated successfully", id);
            return appointmentDAO.safeSave(existingAppointment);
        } catch (DataAccessException ex) {
            logger.error("Error updating appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to update appointment with ID: " + id, ex);
        }
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id The ID of the appointment to delete.
     * @throws ServiceException if the appointment is not found or there is a deletion error.
     */
    public void deleteAppointmentById(Long id) {
        try {
            logger.info("Deleting appointment with ID: {}", id);
            if (!appointmentDAO.existsById(id)) {
                logger.error("Appointment with ID: {} does not exist", id);
                throw new ServiceException("Appointment with ID " + id + " does not exist");
            }
            appointmentDAO.safeDeleteById(id);
            logger.info("Appointment with ID: {} deleted successfully", id);
        } catch (DataAccessException ex) {
            logger.error("Error deleting appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete appointment with ID: " + id, ex);
        }
    }
}
