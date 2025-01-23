package com.example.PatientManagementSystem.model;

import java.util.List;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

@Entity
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    @Column(name = "phone")
    private String phone;

    @Column(name = "doctor_id")
    private Long doctorId;

    @ManyToOne
    @Schema(hidden = true)
    @JoinColumn(name = "doctor_id", insertable = false, updatable = false, nullable = false)
    private Doctor doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @PastOrPresent(message = "Admit date must be in the past or present")
    @Column(name = "admit_date")
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
