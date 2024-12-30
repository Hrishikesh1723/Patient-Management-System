package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDAO extends JpaRepository<Patient, Long>{
}
