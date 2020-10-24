package com.fundoo.note.service;

import com.fundoo.note.dto.NoteColorDto;
import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.ReminderDto;
import com.fundoo.note.dto.SortDto;
import com.fundoo.note.enumerations.GetNote;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.user.dto.ResponseDto;

import java.io.IOException;
import java.util.List;

public interface INoteService {
    ResponseDto createNote(NoteDto noteDto, String email) throws IOException;

    ResponseDto deleteNote(int note_id, String email) throws NoteException, IOException;

    ResponseDto trashNoteDelete(int note_id, String email) throws NoteException, IOException;

    boolean updateNote(NoteDto noteId, String email) throws NoteException;

    List<Note> getNoteList(String email);

    List<Note> sort(SortDto sortDto, String email);

    boolean pinUnpinNote(int note_id, String email) throws NoteException;

    boolean archive(int note_id, String email) throws NoteException;

    List<Note> getNotes(GetNote value, String email);

    boolean restoreTrashNote(int note_id) throws NoteException;

    boolean setReminder(ReminderDto reminderDto, String email) throws NoteException;

    boolean deleteReminder(int note_id, String email) throws NoteException;

    List<Note> getReminderSetNotes();

    boolean setNoteColor(NoteColorDto noteColorDto, String email) throws NoteException;

    List<Note> getMapNote(String email, Integer label_id) throws NoteException;
}
