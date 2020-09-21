package com.fundoo.collaborator.service;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;

import java.util.List;

public interface ICollaborateService {
    boolean addCollaborator(CollaborateNoteDto collaborateNoteDto, String email) throws NoteException;

    List<Note> getCollaboratorNotes(String email) throws NoteException;
}
