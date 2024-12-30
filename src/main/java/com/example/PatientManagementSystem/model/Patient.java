package com.example.PatientManagementSystem.model;

import java.util.List;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

@Entity
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportList;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @PastOrPresent(message = "Admit date must be in the past or present")
    private LocalDate admitDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<Report> getReportList() { return reportList; }
    public void setReportList(List<Report> reportList) { this.reportList = reportList; }
    public Doctor getDoctor() {return  doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public LocalDate getAdmitDate() { return admitDate; }
    public void setAdmitDate(LocalDate admitDate) { this.admitDate = admitDate; }
}
