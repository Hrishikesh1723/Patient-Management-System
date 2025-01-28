package com.example.PatientManagementSystem.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import  jakarta.persistence.*;

/**
 * Entity representing a Department.
 */
@Entity
@Table(name = "departments")
@Schema(description = "Represents a department in the hospital, such as Cardiology or Pediatrics.")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    @Schema(description = "Unique identifier for the department.", example = "1")
    private Long departmentId;

    @Column(name = "department_name")
    @Schema(description = "Name of the department.", example = "Cardiology")
    private String departmentName;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true) // Hides this field from Swagger UI
    private List<Doctor> doctors;

    // Getter and setters
    public Long getDepartmentID () { return departmentId; }
    public void setDepartmentID(Long id) { this.departmentId = id; }
    public  String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public List<Doctor> getDoctors() { return doctors; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }

}
