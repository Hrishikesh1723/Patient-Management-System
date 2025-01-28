package com.example.PatientManagementSystem.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing an Appointment.
 */
@Entity
@Table(name = "appointments")
@Schema(description = "Represents an appointment in the Patient Management System.")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    @Schema(description = "Unique identifier for the appointment.", example = "1")
    private Long appointmentId;

    @Column(name = "patient_id")
    @Schema(description = "ID of the patient associated with the appointment.", example = "41")
    private Long patientId;

    @Column(name = "doctor_id")
    @Schema(description = "ID of the doctor associated with the appointment.", example = "22")
    private Long doctorId;

    @ManyToOne
    @Schema(hidden = true)// Hides this field from Swagger UI
    @JoinColumn(name = "patient_id", insertable = false, updatable = false, nullable = false)
    private Patient patient;

    @ManyToOne
    @Schema(hidden = true)// Hides this field from Swagger UI
    @JoinColumn(name = "doctor_id", insertable = false, updatable = false, nullable = false)
    private Doctor doctor;

    @Column(name = "appointment_date")
    @Schema(description = "Date and time of the appointment.", example = "2025-02-01T10:30:00")
    private LocalDateTime appointmentDate;

    // Getters and setters

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
}
