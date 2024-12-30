package com.example.PatientManagementSystem.model;

import java.util.*;
import  jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors;

    // Getter and setters
    public Long getDepartmentID () { return departmentId; }
    public void setDepartmentID(Long id) { this.departmentId = id; }
    public  String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public List<Doctor> getDoctors() { return doctors; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }
}
