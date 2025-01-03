package com.example.PatientManagementSystem.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "department_id")
    private Long departmentId;

    @ManyToOne
    @JoinColumn(name = "department_id", insertable = false, updatable = false, nullable = false)
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients;

    // Getter and setters
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long id) { this.doctorId = id; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department departmentId) { this.department = departmentId; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public List<Patient> getPatients() { return patients; }
    public void setPatients(List<Patient> patients) { this.patients = patients; }
}


