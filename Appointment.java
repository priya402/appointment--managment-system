package com.AppointmentManagment.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment") // Maps to the "appointment" table in PostgreSQL
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(name = "user_id", nullable = false) // Maps to the "user_id" column
    private String userId;

    @Column(name = "user_name", nullable = false, length = 100) // Maps to "user_name" column
    private String userName;

    @Column(name = "doctor_id", nullable = false) // Maps to the "doctor_id" column
    private String doctorId;

   
    @Column(name = "appointment_date", nullable = false) // Maps to the "appointment_date" column
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false) // Maps to the "appointment_time" column
    private LocalTime appointmentTime;

    @Column(name = "symptoms", length = 500) // Maps to "symptoms" column
    private String symptoms;

    @Column(name = "communication_mode", nullable = false, length = 50) // Maps to "communication_mode" column
    private String communicationMode;

    
    @Column(name = "is_complete", nullable = false) // Maps to the "is_complete" column
    private boolean isComplete;
    
    @Column(name = "status", length = 500) // Maps to "symptoms" column
    private String status;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getCommunicationMode() {
        return communicationMode;
    }

    public void setCommunicationMode(String communicationMode) {
        this.communicationMode = communicationMode;
    }

   

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
