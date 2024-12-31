package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorDAO extends JpaRepository<Doctor, Long>{
}

