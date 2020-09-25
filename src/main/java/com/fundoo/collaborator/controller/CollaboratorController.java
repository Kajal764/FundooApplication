package com.fundoo.collaborator.controller;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.collaborator.service.ICollaborateService;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.user.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/fundoo/note")
public class CollaboratorController {

    @Autowired
    ICollaborateService collaborateService;

    @PostMapping("/collaborators")
    public ResponseDto addCollaborator(@RequestBody CollaborateNoteDto collaborateNoteDto, HttpServletRequest request) throws NoteException {
        String email = (String) request.getAttribute("email");
        if (collaborateService.addCollaborator(collaborateNoteDto, email))
            return new ResponseDto("Collaborated User Successfully", 200);
        return new ResponseDto("User Not Collaborate", 400);
    }

    @GetMapping("/collaborateNotes")
    public List<Note> getCollaboratorNote(HttpServletRequest request) throws NoteException {
        String email = (String) request.getAttribute("email");
        return collaborateService.getCollaboratorNotes(email);
    }

    @DeleteMapping("/removeCollaborate")
    public ResponseDto deleteCollaborateNote(@RequestBody CollaborateNoteDto collaborateNoteDto, HttpServletRequest request) throws NoteException {
        if (collaborateService.removeCollaboration(collaborateNoteDto))
            return new ResponseDto("Collaboration remove Successfully", 200);
        return new ResponseDto("Collaboration Not Remove", 400);
    }

}
