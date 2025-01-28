package com.example.PatientManagementSystem.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * Entity representing a Report.
 */
@Entity
@Table(name = "reports")
@Schema(description = "Represents a reports of a patient in the patient management system.")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    @Schema(description = "Unique identifier for the report.", example = "1")
    private Long reportId;

    @Column(name = "report_name")
    @Schema(description = "Name of the Report.", example = "Blood Pressure Report")
    private String reportName;

    @Column(name = "Patient_id")
    @Schema(description = "Unique identifier for the patient.", example = "21")
    private Long patientId;

    @ManyToOne
    @Schema(hidden = true)// Hides this field from Swagger UI
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
