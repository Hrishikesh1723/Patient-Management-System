package com.example.PatientManagementSystem.service;

import com.example.PatientManagementSystem.exception.DataAccessException;
import com.example.PatientManagementSystem.exception.ServiceException;
import com.example.PatientManagementSystem.model.Doctor;
import com.example.PatientManagementSystem.dao.DoctorDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    private DoctorDAO doctorDAO;

    public Doctor saveDoctor(Doctor doctor) {
        try {
            logger.info("Saving doctor: {}", doctor.getDoctorName());
            return doctorDAO.safeSave(doctor);
        } catch (DataAccessException ex) {
            logger.error("Error saving doctor: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to save doctor", ex);
        }
    }

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

    public List<Doctor> getAllDoctors() {
        try {
            logger.info("Fetching all doctors");
            return doctorDAO.findAll();
        } catch (Exception ex) {
            logger.error("Error fetching all doctors: {}", ex.getMessage(), ex);
            throw new ServiceException("Failed to fetch all doctors", ex);
        }
    }

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
