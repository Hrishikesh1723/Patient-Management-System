package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDAO extends JpaRepository<Patient, Long>{
}