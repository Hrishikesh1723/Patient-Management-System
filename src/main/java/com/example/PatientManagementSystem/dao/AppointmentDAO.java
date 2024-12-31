package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDAO extends JpaRepository<Appointment, Long>{
}
