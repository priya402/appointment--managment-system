package com.NotesService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NotesService.Entity.Note;
import com.NotesService.Service.NoteService;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*")
public class NoteController {

	@Autowired
    private NoteService noteService;

    // Save a new note
    @PostMapping("/add")
    public Note addNote(@RequestBody Note note) {
    	System.out.println(note.toString());
        return noteService.saveNote(note);
    }

    // Get all notes
    @GetMapping("/all")
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    // Get notes by patient ID
    @GetMapping("/patient/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable String patientId) {
        return noteService.getNotesByPatientId(patientId);
    }

    // Get notes by doctor ID
    @GetMapping("/doctor/{doctorId}")
    public List<Note> getNotesByDoctorId(@PathVariable String doctorId) {
        return noteService.getNotesByDoctorId(doctorId);
    }
}
