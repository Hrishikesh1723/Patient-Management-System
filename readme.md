# Patient Management System API

This project provides a robust backend API for managing key entities in a **Patient Management System**, including Patients, Departments, Doctors, Reports, and Appointments. The API supports CRUD operations and features like pagination, sorting, and association handling to streamline healthcare workflows.

---

## Features

- **Patient Management**: Add, update, delete, and fetch patient details.
- **Department Management**: Manage hospital departments with CRUD operations.
- **Doctor Management**: Handle doctor information and their department associations.
- **Report Management**: Create, retrieve, and delete patient reports.
- **Appointment Management**: Schedule and manage doctor-patient appointments.

---

## Entities

1. **Patient**: Manage patient records, including admission details and assigned doctors.
2. **Department**: Create and manage hospital departments.
3. **Doctor**: Manage doctor profiles and department affiliations.
4. **Report**: Maintain diagnostic reports linked to patients.
5. **Appointment**: Schedule and update doctor-patient appointments.

---

## API Documentation

Explore the API endpoints using the **Postman Collection**:
[Postman Collection Link](https://documenter.getpostman.com/view/22921912/2sAYBd67fz) 

---

## Getting Started

### Prerequisites
- Java (version 23 or later)
- Spring Boot
- Postman (for testing the APIs)

### Setup
1. Clone the repository:
   ```bash
    git clone https://github.com/Hrishikesh1723/PatientManagementSystem
   ```
2. Navigate to the project directory:
   ```bash
   cd patient-management-system
   ```
3. Build and run the project:
   ```bash
   ./mvnw spring-boot:run
   ```

## Usage

### Base URL
All API endpoints are prefixed with:  
```bash
  http://localhost:5000/api/v1/
```

### Example Endpoints
- **Create a new patient**: `POST /patients`
- **Fetch patient list**: `GET /patients`
- **Update patient details**: `PUT /patients/{id}`
- **Delete a patient**: `DELETE /patients/{id}`

Refer to the [Postman Collection](https://documenter.getpostman.com/view/22921912/2sAYBd67fz)  for detailed API usage.

---

## Repository

Find the complete source code in the GitHub repository:  
[GitHub Repository Link](https://github.com/Hrishikesh1723/PatientManagementSystem) 
---

