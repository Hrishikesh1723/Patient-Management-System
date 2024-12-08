package com.example.Patient_Management_System.service;

import com.example.Patient_Management_System.model.Patient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {
    private final Map<String, Patient> patientMap = new HashMap<>();

    public void addPatient(Patient patient) {
        patientMap.put(patient.getId(), patient);
    }

    public Patient getPatientById(String id) {
        return patientMap.get(id);
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    public void updatePatient(String id, Patient updatedPatient) {
        patientMap.put(id, updatedPatient);
    }

    public void deletePatientById(String id) {
        patientMap.remove(id);
    }
}
