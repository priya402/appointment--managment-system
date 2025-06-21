package com.AppointmentManagment.Controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.AppointmentManagment.Entity.Appointment;
import com.AppointmentManagment.Service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/appointments/")
@CrossOrigin(origins = "*")
@EnableDiscoveryClient
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	

    @Autowired
    private RestTemplate restTemplate;

	// Get all appointments
	@GetMapping
	public ResponseEntity<List<Appointment>> getAllAppointments() {
		try {
			List<Appointment> appointments = appointmentService.getAllAppointments();
			if (appointments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get an appointment by ID
	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
		try {
			Appointment appointment = appointmentService.getAppointmentById(id);
			if (appointment != null) {
				return new ResponseEntity<>(appointment, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("User/{id}")
	public ResponseEntity<List<Appointment>> getAppointmentByUserId(@PathVariable String id) {
		try {
			List<Appointment> appointment = appointmentService.getAppointmentsByUserId(id);
			if (appointment != null) {
				return new ResponseEntity<>(appointment, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("Doctor/{id}")
	public ResponseEntity<List<Appointment>> getAppointmentByDoctorId(@PathVariable String id) {
		try {
			List<Appointment> appointment = appointmentService.getAppointmentsByDoctorId(id);
			if (appointment != null) {
				return new ResponseEntity<>(appointment, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// Create a new appointment
	@PostMapping
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
	    try {
	        // Create the appointment
	        Appointment newAppointment = appointmentService.createAppointment(appointment);
	        System.out.println(appointment.getUserId());

	        // Fetch user details
	        String userServiceUrl = "http://user-service/pationt/patientsDetails/id/" + appointment.getUserId();
	        Map<String, Object> user = fetchDetailsFromService(userServiceUrl);

	        // Fetch doctor details
	        String doctorServiceUrl = "http://user-service/doctor/doctor/id/" + appointment.getDoctorId();
	        Map<String, Object> doctor = fetchDetailsFromService(doctorServiceUrl);

	        if (user == null || doctor == null) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	        // Extract required fields
	        String userName = (String) user.get("name");
	        String userEmail = (String) user.get("email");
	        String doctorName = (String) doctor.get("name");

	        // Prepare email content
	      

	        // Prepare email payload
	        Map<String, String> mailPayload = new LinkedHashMap<>();
	        mailPayload.put("user_email", userEmail);
	        mailPayload.put("user_name", userName);
	        mailPayload.put("doctor", doctorName);
	        mailPayload.put("appointment_date", newAppointment.getAppointmentDate().toString());
	        mailPayload.put("appointment_time", newAppointment.getAppointmentTime().toString());


	        // Send confirmation email
	        sendConfirmationEmail(mailPayload);

	        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	private Map<String, Object> fetchDetailsFromService(String url) {
	    try {
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
	        return response.getBody();
	    } catch (Exception e) {
	        System.out.println("Error fetching data from " + url + ": " + e.getMessage());
	        return null;
	    }
	}


	private void sendConfirmationEmail(Map<String, String> mailPayload) {
	    String mailServiceUrl = "http://mail-service/Mails/appointmentConfirmation/";

	    try {
	        RestTemplate restTemplate = new RestTemplate();
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
	        // Log payload before sending
	        System.out.println("Sending mail payload: " + mailPayload.toString());

	        // Convert Map to JSON String using ObjectMapper
	        ObjectMapper objectMapper = new ObjectMapper();
	        String jsonPayload = objectMapper.writeValueAsString(mailPayload);

	        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

	        ResponseEntity<String> response = restTemplate.postForEntity(mailServiceUrl, request, String.class);

	        // Check if the email was successfully sent
	        if (!response.getStatusCode().is2xxSuccessful()) {
	            System.out.println("Failed to send confirmation email: " + response.getBody());
	        } else {
	            System.out.println("Email sent successfully!");
	        }
	    } catch (Exception e) {
	        System.out.println("Error while sending email: " + e.getMessage());
	    }
	}





	// Update an existing appointment
	@PutMapping("/{id}/status")
	public ResponseEntity<?> updateAppointmentStatus(@PathVariable Long id, @RequestBody Map<String, Object> updateData) {
	    try {
	        boolean isCompleted = (boolean) updateData.getOrDefault("isCompleted", false);
	        String status = (String) updateData.getOrDefault("status", "Not Completed");

	        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, isCompleted, status);
	        if (updatedAppointment != null) {
	            return ResponseEntity.ok(updatedAppointment);
	        }
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating appointment status: " + e.getMessage());
	    }
	}


	// Delete an appointment
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
		try {
			boolean isDeleted = appointmentService.deleteAppointment(id);
			if (isDeleted) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Delete all appointments
	@DeleteMapping
	public ResponseEntity<Void> deleteAllAppointments() {
		try {
			appointmentService.deleteAllAppointments();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<Appointment> updateAppointmentDetails(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
	    try {
	        // Fetch the existing appointment by ID
	        Appointment existingAppointment = appointmentService.getAppointmentById(id);
	        
	        if (existingAppointment != null) {
	            // Update the fields of the existing appointment
	            existingAppointment.setUserName(updatedAppointment.getUserName());
	            existingAppointment.setDoctorId(updatedAppointment.getDoctorId());
	            existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
	            existingAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
	            existingAppointment.setSymptoms(updatedAppointment.getSymptoms());
	            existingAppointment.setCommunicationMode(updatedAppointment.getCommunicationMode());
	            existingAppointment.setComplete(updatedAppointment.isComplete());
	            existingAppointment.setStatus(updatedAppointment.getStatus());
	            
	            // Save the updated appointment
	            Appointment savedAppointment = appointmentService.updateAppointment(existingAppointment);
	            return new ResponseEntity<>(savedAppointment, HttpStatus.OK);
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
}
