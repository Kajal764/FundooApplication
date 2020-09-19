package com.fundoo.note.controller;

import com.fundoo.label.exception.LabelException;
import com.fundoo.label.model.Label;
import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.service.INoteService;
import com.fundoo.user.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/fundoo/note")
public class NoteController {

    @Autowired
    INoteService noteService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createNote(@Valid @RequestBody NoteDto noteDto, ServletRequest request) {
        Object email = request.getAttribute("email");
        return noteService.createNote(noteDto, (String) email);
    }

    @PutMapping(value = "/delete/{note_Id}")
    public ResponseDto trashNote(@PathVariable("note_Id") int note_id, HttpServletRequest request) throws NoteException {
        Object email = request.getAttribute("email");
        ResponseDto trash = noteService.deleteNote(note_id, (String) email);
        return trash;
    }

    @DeleteMapping(value = "/trash/{note_Id}")
    public ResponseDto deleteNote(@PathVariable("note_Id") int note_id,HttpServletRequest request) throws NoteException {
        Object email = request.getAttribute("email");
        return noteService.trashNoteDelete(note_id, (String) email);
    }

    @PutMapping(value = "/update")
    public Object updateNote(@RequestBody NoteDto noteDto, HttpServletRequest request) throws NoteException {
        Object email = request.getAttribute("email");
        if(noteService.updateNote(noteDto, (String) email))
            return new ResponseDto("Note Updated",200);
        return new ResponseDto("Error Updating Note",400);
    }

    @GetMapping("/fetchList")
    public List<Note> fetchVerifiedUser(HttpServletRequest request){
        String email = (String) request.getAttribute("email");
        List<Note> list = noteService.getNoteList(email);
        if(list.isEmpty())
            throw new LabelException("Note Not Found",400);
        return list;
    }

}

