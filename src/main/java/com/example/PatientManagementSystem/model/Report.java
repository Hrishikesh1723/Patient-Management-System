package com.example.PatientManagementSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String reportName;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Getters and setters
    public Long getReportId() { return reportId; }
    public void setReportId(Long id) { this.reportId = id; }
    public  String getReportName() { return reportName; }
    public void setReportName(String reportName) { this.reportName = reportName; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
}
