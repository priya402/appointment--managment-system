package com.AppointmentManagment.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppointmentManagment.Entity.Appointment;
import com.AppointmentManagment.Reposetory.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * Fetch all appointments from the database.
     *
     * @return List of all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Fetch a specific appointment by its ID.
     *
     * @param id ID of the appointment to fetch.
     * @return The appointment with the given ID or null if not found.
     */
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }
    
    
    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
    
    public List<Appointment> getAppointmentsByUserId(String userId) {
        return appointmentRepository.findByUserId(userId);
    }

    /**
     * Create a new appointment and save it to the database.
     *
     * @param appointment Appointment object to save.
     * @return The saved appointment object.
     */
    public Appointment createAppointment(Appointment appointment) {
        try {
            return appointmentRepository.save(appointment);
        } catch (Exception e) {
            System.out.println("Error creating appointment: " + e.getMessage());
            e.printStackTrace(); // Log the exception stack trace
            throw new RuntimeException("Error creating appointment", e);
        }
    }


    /**
     * Update an existing appointment by its ID.
     *
     * @param id                ID of the appointment to update.
     * @param updatedAppointment The updated appointment object.
     * @return The updated appointment object or null if not found.
     */
    public Appointment updateAppointmentStatus(Long id, boolean isCompleted, String status) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        
        if (optionalAppointment.isPresent()) {
            Appointment existingAppointment = optionalAppointment.get();
            existingAppointment.setComplete(isCompleted); // Set the isCompleted field
            existingAppointment.setStatus(status); // Set the status field

            return appointmentRepository.save(existingAppointment);
        }
        return null;
    }

    /**
     * Delete an appointment by its ID.
     *
     * @param id ID of the appointment to delete.
     */
    public boolean deleteAppointment(Long id) {
        try {
            if (appointmentRepository.existsById(id)) {
                appointmentRepository.deleteById(id);
                return true; // Return true if the appointment was successfully deleted
            } else {
                return false; // Return false if the appointment with the given ID does not exist
            }
        } catch (Exception e) {
            // Handle any potential exceptions (e.g., database errors)
            return false;
        }
    }
    
    public boolean deleteAllAppointments() {
        try {
            if (appointmentRepository.count() > 0) {
                appointmentRepository.deleteAll();
                return true; // Return true if appointments were successfully deleted
            } else {
                return false; // Return false if there are no appointments to delete
            }
        } catch (Exception e) {
            // Handle any potential exceptions (e.g., database errors)
            return false;
        }
    }
    public Appointment updateAppointment(Appointment appointment) {
        // Assuming you have a repository or DAO to interact with the database
        return appointmentRepository.save(appointment);  // This will update the existing appointment
    }


}
