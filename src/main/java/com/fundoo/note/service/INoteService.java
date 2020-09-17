package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.user.dto.ResponseDto;

public interface INoteService {
    ResponseDto createNote(NoteDto noteDto, String email);

    ResponseDto deleteNote(int note_id, String email) throws NoteException;

    ResponseDto trashNoteDelete(int note_id, String email) throws NoteException;

    boolean updateNote(NoteDto noteId, String email) throws NoteException;
}
