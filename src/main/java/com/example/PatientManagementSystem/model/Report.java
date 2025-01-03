package com.example.PatientManagementSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "Patient_id")
    private Long patientId;

    @ManyToOne
    @JoinColumn(name = "patient_id", insertable = false, updatable = false, nullable = false)
    private Patient patient;

    // Getters and setters
    public Long getReportId() { return reportId; }
    public void setReportId(Long id) { this.reportId = id; }
    public  String getReportName() { return reportName; }
    public void setReportName(String reportName) { this.reportName = reportName; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
}
