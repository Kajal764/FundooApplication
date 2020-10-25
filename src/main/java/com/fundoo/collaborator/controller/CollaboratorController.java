package com.fundoo.collaborator.controller;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.collaborator.service.ICollaborateService;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.model.User;
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
    public ResponseDto addCollaborator(@RequestBody CollaborateNoteDto collaborateNoteDto, HttpServletRequest request, @RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        String email = (String) request.getAttribute("email");
        if (collaborateService.addCollaborator(collaborateNoteDto, email))
            return new ResponseDto("Collaborated User Successfully", 200);
        return new ResponseDto("User Not Collaborate", 400);
    }

    @GetMapping("/collaborateNotes")
    public List<Note> getCollaboratorNote(HttpServletRequest request, @RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        String email = (String) request.getAttribute("email");
        return collaborateService.getCollaboratorNotes(email);
    }

    @PutMapping("/removeCollaborate")
    public ResponseDto deleteCollaborateNote(@RequestBody CollaborateNoteDto collaborateNoteDto, HttpServletRequest request, @RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        if (collaborateService.removeCollaboration(collaborateNoteDto))
            return new ResponseDto("Collaboration remove Successfully", 200);
        return new ResponseDto("Collaboration Not Remove", 400);
    }

    @GetMapping("/collaborateUser/{note_Id}")
    public List<User> getCollaboratUser(@PathVariable("note_Id") int note_id, HttpServletRequest request, @RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        String email = (String) request.getAttribute("email");
        return collaborateService.getCollaboratUsers(email, note_id);
    }

}
