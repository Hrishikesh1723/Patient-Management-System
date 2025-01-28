package com.example.PatientManagementSystem.model;

import java.util.List;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

/**
 * Entity representing a Patient.
 */
@Entity
@Table(name="patients")
@Schema(description = "Represents a patient in the patient management system.")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    @Schema(description = "Unique identifier for the patient.", example = "1")
    private Long patientId;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    @Schema(description = "Name of the Patient.", example = "Gregory Hall")
    private String name;

    @Email(message = "Invalid email format")
    @Column(name = "email")
    @Schema(description = "Email of the Patient.", example = "gregory.hall@example.com")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    @Column(name = "phone")
    @Schema(description = "Phone of the Patient.", example = "555-8901")
    private String phone;

    @Column(name = "doctor_id")
    @Schema(description = "Unique identifier for the doctor.", example = "21")
    private Long doctorId;

    @ManyToOne
    @Schema(hidden = true)// Hides this field from Swagger UI
    @JoinColumn(name = "doctor_id", insertable = false, updatable = false, nullable = false)
    private Doctor doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true)// Hides this field from Swagger UI
    private List<Report> reports;

    @PastOrPresent(message = "Admit date must be in the past or present")
    @Column(name = "admit_date")
    @Schema(description = "Date of admission of patient.", example = "2025-01-27")
    private LocalDate admitDate;

    // Getters and Setters
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Long getDoctorId() {return  doctorId; }
    public void setDoctorId(Long doctor) { this.doctorId = doctor; }
    public LocalDate getAdmitDate() { return admitDate; }
    public void setAdmitDate(LocalDate admitDate) { this.admitDate = admitDate; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public List<Report> getReports() { return reports; }
    public void setReports(List<Report> reports) { this.reports = reports; }
}
