package com.fundoo.note.controller;

import com.fundoo.label.exception.LabelException;
import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.ReminderDto;
import com.fundoo.note.dto.SortDto;
import com.fundoo.note.enumerations.GetNote;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.exception.ReminderException;
import com.fundoo.note.model.Note;
import com.fundoo.note.service.IElasticSearchService;
import com.fundoo.note.service.INoteService;
import com.fundoo.user.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fundoo/note")
public class NoteController {

    @Autowired
    INoteService noteService;

    @Autowired
    IElasticSearchService IElasticSearchService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createNote(@Valid @RequestBody NoteDto noteDto, @RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken, HttpServletRequest request) throws IOException {
        Object email = request.getAttribute("email");
        return noteService.createNote(noteDto, (String) email);
    }

    @PutMapping(value = "/delete/{note_Id}")
    public ResponseDto trashNote(@PathVariable("note_Id") int note_id, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        Object email = request.getAttribute("email");
        ResponseDto trash = noteService.deleteNote(note_id, (String) email);
        return trash;
    }

    @DeleteMapping(value = "/trash/{note_Id}")
    public ResponseDto deleteNote(@PathVariable("note_Id") int note_id, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException, IOException {
        Object email = request.getAttribute("email");
        return noteService.trashNoteDelete(note_id, (String) email);
    }

    @PutMapping(value = "/restoreTrashNote/{note_Id}")
    public ResponseDto restoreTrashNote(@PathVariable("note_Id") int note_id, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        if (noteService.restoreTrashNote(note_id))
            return new ResponseDto("Note Restored", 200);
        return new ResponseDto("Error Restoring Note", 400);
    }

    @PutMapping(value = "/update")
    public Object updateNote(@RequestBody NoteDto noteDto, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        Object email = request.getAttribute("email");
        if (noteService.updateNote(noteDto, (String) email))
            return new ResponseDto("Note Updated", 200);
        return new ResponseDto("Error Updating Note", 400);
    }

    @GetMapping("/list")
    public List<Note> getNoteList(HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) {
        String email = (String) request.getAttribute("email");
        List<Note> list = noteService.getNoteList(email);
        if (list.isEmpty())
            throw new LabelException("Note Not Found", 400);
        return list;
    }

    @PostMapping(value = "/sort")
    public List<Note> sortList(@RequestBody SortDto sortDto, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) {
        String email = (String) request.getAttribute("email");
        List<Note> sortList = noteService.sort(sortDto, email);
        if (sortList.isEmpty())
            throw new LabelException("List Not Found", 400);
        return sortList;
    }

    @PutMapping(value = "/pinUnpin/{note_Id}")
    public ResponseDto pinUnpinNote(@PathVariable("note_Id") int note_id, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        String email = (String) request.getAttribute("email");
        if (noteService.pinUnpinNote(note_id, email))
            return new ResponseDto("Note Pin", 200);
        return new ResponseDto("Note Unpin", 200);
    }

    @PutMapping(value = "/archive/{note_Id}")
    public ResponseDto pinArchiveNote(@PathVariable("note_Id") int note_id, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        String email = (String) request.getAttribute("email");
        if (noteService.archive(note_id, email)) {
            return new ResponseDto("Note Archived", 200);
        }
        return new ResponseDto("Note Unarchived", 200);
    }

    @GetMapping("/trashList")
    public List<Note> fetchTrashNotes(HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) {
        String email = (String) request.getAttribute("email");
        GetNote value = GetNote.TRASH;
        List<Note> list = noteService.getNotes(value, email);
        if (list.isEmpty())
            throw new LabelException("Trash is empty", 400);
        return list;
    }

    @GetMapping("/archiveList")
    public List<Note> fetchArchiveNotes(HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) {
        String email = (String) request.getAttribute("email");
        GetNote value = GetNote.ARCHIVE;
        List<Note> list = noteService.getNotes(value, email);
        if (list.isEmpty())
            throw new LabelException("empty", 400);
        return list;
    }

    @GetMapping("/pinList")
    public List<Note> fetchPinNotes(HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) {
        String email = (String) request.getAttribute("email");
        GetNote value = GetNote.PIN;
        List<Note> list = noteService.getNotes(value, email);
        if (list.isEmpty())
            throw new LabelException("empty", 400);
        return list;
    }

    @PutMapping(value = "/reminder")
    public ResponseDto setReminder(@RequestBody ReminderDto reminderDTO, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        String email = (String) request.getAttribute("email");
        if (noteService.setReminder(reminderDTO, email))
            return new ResponseDto("Reminder Set", 200);
        return new ResponseDto("Reminder Not Set", 400);
    }

    @DeleteMapping("/reminder")
    public ResponseDto deleteReminder(@RequestBody ReminderDto reminderDTO, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws NoteException {
        String email = (String) request.getAttribute("email");
        if (noteService.deleteReminder(reminderDTO, email))
            return new ResponseDto("Reminder Delete", 200);
        return new ResponseDto("Reminder Not Delete", 400);
    }

    @GetMapping("/reminder")
    public List<Note> getReminderNotes(HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) {
        List<Note> reminderSetNotes = noteService.getReminderSetNotes();
        if (reminderSetNotes.isEmpty())
            throw new ReminderException("Reminder Note Not Found", 400);
        return reminderSetNotes;
    }

    @GetMapping("/searchData/{title}")
    public List<Note> searchTitle(@PathVariable("title") String title, HttpServletRequest request,@RequestHeader(value = "AuthorizeToken", required = false) String AuthorizeToken) throws IOException {
        String email = (String) request.getAttribute("email");
        return IElasticSearchService.searchByTitle(title, email);
    }

}

