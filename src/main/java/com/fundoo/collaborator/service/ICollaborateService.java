package com.fundoo.collaborator.service;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.user.model.User;
import com.sun.mail.imap.protocol.UIDSet;

import javax.mail.MessagingException;
import java.util.List;

public interface ICollaborateService {
    boolean addCollaborator(CollaborateNoteDto collaborateNoteDto, String email) throws NoteException, MessagingException;

    List<Note> getCollaboratorNotes(String email) throws NoteException;

    boolean removeCollaboration(CollaborateNoteDto collaborateNoteDto) throws NoteException;

    List<User> getCollaboratUsers(String email, int note_id) throws NoteException;
}
