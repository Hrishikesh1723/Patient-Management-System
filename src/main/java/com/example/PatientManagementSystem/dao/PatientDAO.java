package com.example.PatientManagementSystem.dao;

import com.example.PatientManagementSystem.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientDAO {
    private final List<Patient> patientList = new ArrayList<>();
    private long idCounter = 1;

    public Patient save(Patient patient) {
        patient.setId(idCounter++);
        patientList.add(patient);
        return patient;
    }

    public Optional<Patient> findById(Long id) {
        return patientList.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Patient> findAll() {
        return patientList;
    }

    public void deleteById(Long id) {
        patientList.removeIf(p -> p.getId().equals(id));
    }
}