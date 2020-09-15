package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.user.dto.ResponseDto;

public interface INoteService {
    ResponseDto createNote(NoteDto noteDto, String token);

    ResponseDto trash(int note_id, String token) throws NoteException;

    ResponseDto deleteNote(int note_id, String token) throws NoteException;
}
