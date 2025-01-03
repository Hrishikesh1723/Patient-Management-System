package com.example.PatientManagementSystem.service;

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
        logger.info("Saving appointment for patient");
        return appointmentDAO.save(appointment);
    }

    public Appointment getAppointmentById(Long id) {
        logger.info("Retrieving appointment by ID: {}", id);
        return appointmentDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Appointment not found with ID: {}", id);
                    return new IllegalArgumentException("Appointment not found with ID: " + id);
                });
    }

    public List<Appointment> getAllAppointments() {
        logger.info("Fetching all appointments");
        return appointmentDAO.findAll();
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        logger.info("Updating appointment with ID: {}", id);
        Appointment existingAppointment = getAppointmentById(id);
        existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setDoctorId(updatedAppointment.getDoctorId());
        existingAppointment.setPatientId(updatedAppointment.getPatientId());
        logger.info("Appointment with ID: {} updated successfully", id);
        return appointmentDAO.save(existingAppointment);
    }

    public void deleteAppointmentById(Long id) {
        logger.info("Deleting appointment with ID: {}", id);
        appointmentDAO.deleteById(id);
        logger.info("Appointment with ID: {} deleted successfully", id);
    }
}
