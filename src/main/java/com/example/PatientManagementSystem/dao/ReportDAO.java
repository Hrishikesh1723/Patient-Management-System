package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDAO extends JpaRepository<Report, Long>{
}
