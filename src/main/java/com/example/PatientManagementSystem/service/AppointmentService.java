package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Appointment;
import com.example.PatientManagementSystem.dao.AppointmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentDAO appointmentDAO;

    public Appointment saveAppointment(Appointment appointment) {
        try {
            logger.info("Saving appointment for patient");
            return appointmentDAO.save(appointment);
        } catch (Exception ex) {
            logger.error("Error saving appointment: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save appointment", ex);
        }
    }

    public Appointment getAppointmentById(Long id) {
        try {
            logger.info("Retrieving appointment by ID: {}", id);
            return appointmentDAO.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Appointment not found with ID: {}", id);
                        return new ServiceException("Appointment not found with ID: " + id);
                    });
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error retrieving appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to retrieve appointment with ID: " + id, ex);
        }
    }

    public List<Appointment> getAllAppointments() {
        try {
            logger.info("Fetching all appointments");
            return appointmentDAO.findAll();
        } catch (Exception ex) {
            logger.error("Error fetching all appointments: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all appointments", ex);
        }
    }

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
            return appointmentDAO.save(existingAppointment);
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error updating appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to update appointment with ID: " + id, ex);
        }
    }

    public void deleteAppointmentById(Long id) {
        try {
            logger.info("Deleting appointment with ID: {}", id);
            if (!appointmentDAO.existsById(id)) {
                logger.error("Appointment with ID: {} does not exist", id);
                throw new ServiceException("Appointment with ID " + id + " does not exist");
            }
            appointmentDAO.deleteById(id);
            logger.info("Appointment with ID: {} deleted successfully", id);
        } catch (ServiceException ex) {
            throw ex; // Re-throw specific exceptions
        } catch (Exception ex) {
            logger.error("Error deleting appointment with ID {}: {}", id, ex.getMessage(), ex);
            throw new ServiceException("Failed to delete appointment with ID: " + id, ex);
        }
    }
}
