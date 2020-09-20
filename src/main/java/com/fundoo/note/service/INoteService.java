package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.SortDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.user.dto.ResponseDto;

import java.util.List;

public interface INoteService {
    ResponseDto createNote(NoteDto noteDto, String email);

    ResponseDto deleteNote(int note_id, String email) throws NoteException;

    ResponseDto trashNoteDelete(int note_id, String email) throws NoteException;

    boolean updateNote(NoteDto noteId, String email) throws NoteException;

    List<Note> getNoteList(String email);

    List<Note> sort(SortDto sortDto, String email);

    boolean pinUnpinNote(int note_id, String email);

    boolean archive(int note_id, String email);
}