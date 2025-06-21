package com.AppointmentManagment.Reposetory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AppointmentManagment.Entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByDoctorId(String doctorId);
    List<Appointment> findByUserId(String userId);

}
