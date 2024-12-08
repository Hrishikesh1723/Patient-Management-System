package com.example.Patient_Management_System.model;

import java.util.List;
import java.time.LocalDate;

public class Patient {
    private String id; // unique identifier
    private String name;
    private String email;
    private String phone;
    private List<String> reportList;
    private LocalDate admitDate;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<String> getReportList() { return reportList; }
    public void setReportList(List<String> reportList) { this.reportList = reportList; }
    public LocalDate getAdmitDate() { return admitDate; }
    public void setAdmitDate(LocalDate admitDate) { this.admitDate = admitDate; }
}
