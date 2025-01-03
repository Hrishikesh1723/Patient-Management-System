package com.example.PatientManagementSystem.service;

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
        logger.info("Saving doctor: {}", doctor.getDoctorName());
        return doctorDAO.save(doctor);
    }

    public Doctor getDoctorById(Long id) {
        logger.info("Retrieving doctor by ID: {}", id);
        return doctorDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with ID: {}", id);
                    return new IllegalArgumentException("Doctor not found with ID: " + id);
                });
    }

    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors");
        return doctorDAO.findAll();
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        logger.info("Updating doctor with ID: {}", id);
        Doctor existingDoctor = getDoctorById(id);
        existingDoctor.setDoctorName(updatedDoctor.getDoctorName());
        existingDoctor.setDepartmentId(updatedDoctor.getDepartmentId());
        existingDoctor.setDepartment(updatedDoctor.getDepartment());
        logger.info("Doctor with ID: {} updated successfully", id);
        return doctorDAO.save(existingDoctor);
    }

    public void deleteDoctorById(Long id) {
        logger.info("Deleting doctor with ID: {}", id);
        doctorDAO.deleteById(id);
        logger.info("Doctor with ID: {} deleted successfully", id);
    }
}
