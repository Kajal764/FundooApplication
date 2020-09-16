package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.user.dto.ResponseDto;

import java.util.Optional;

public interface INoteService {
    ResponseDto createNote(NoteDto noteDto, String token);

    ResponseDto deleteNote(int note_id, String token) throws NoteException;

    ResponseDto trashNoteDelete(int note_id, String token) throws NoteException;

    ResponseDto updateNote(int noteId, String token);
}
