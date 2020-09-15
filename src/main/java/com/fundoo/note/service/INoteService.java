package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.user.dto.ResponseDto;

public interface INoteService {
    ResponseDto createNote(NoteDto noteDto, String token);

}
