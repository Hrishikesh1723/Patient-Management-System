package com.example.PatientManagementSystem.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

/**
 * Entity representing a Doctors.
 */
@Entity
@Table(name = "doctors")
@Schema(description = "Represents a doctor in the patient management system.")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    @Schema(description = "Unique identifier for the doctor.", example = "1")
    private Long doctorId;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "doctor_name")
    @Schema(description = "Name of the doctor.", example = "Dr. John Doe")
    private String doctorName;

    @Column(name = "department_id")
    @Schema(description = "Unique identifier of the department the doctor belongs to.", example = "1")
    private Long departmentId;

    @ManyToOne
    @Schema(hidden = true)// Hides this field from Swagger UI
    @JoinColumn(name = "department_id", insertable = false, updatable = false, nullable = false)
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true) // Hides the patients list from Swagger UI
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


