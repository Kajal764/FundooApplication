package com.fundoo.collaborator.service;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollaborateService implements ICollaborateService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    INoteRepository noteRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Override
    public boolean addCollaborator(CollaborateNoteDto collaborateNoteDto, String email) throws NoteException {
        Optional<User> mainUser = userRepository.findByEmail(email);
        Optional<Note> note = noteRepository.findById(collaborateNoteDto.getNote_Id());
        Optional<User> anotherUser = userRepository.findByEmail(collaborateNoteDto.getEmail());
        if (anotherUser.isEmpty())
            throw new NoteException("User Not Present", 403);
        if (note.get().getCollaboratedUsers().contains(anotherUser.get()))
            throw new NoteException("Note Collaborate Already", 400);
        note.get().getCollaboratedUsers().add(anotherUser.get());
        anotherUser.get().getCollaborateNotes().add(note.get());
        javaMailUtil.sendCollaborationInvite(collaborateNoteDto, mainUser, note);
        noteRepository.save(note.get());
        return true;
    }

    @Override
    public List<Note> getCollaboratorNotes(String email) throws NoteException {
        Optional<User> user = userRepository.findByEmail(email);
        List<Note> collaborateNotes = user.get().getCollaborateNotes();
        if (collaborateNotes.isEmpty())
            throw new NoteException("No Collaborate Note", 404);
        return collaborateNotes;
    }

    @Override
    public boolean removeCollaboration(CollaborateNoteDto collaborateNoteDto) throws NoteException {
        Optional<Note> note = noteRepository.findById(collaborateNoteDto.getNote_Id());
        Optional<User> anotherUser = userRepository.findByEmail(collaborateNoteDto.getEmail());
        if (anotherUser.isEmpty())
            throw new NoteException("User Not Found", 403);
        if (!note.get().getCollaboratedUsers().contains(anotherUser.get()))
            throw new NoteException("Note Not Collaborate", 400);
        note.get().getCollaboratedUsers().remove(anotherUser.get());
        anotherUser.get().getCollaborateNotes().remove(note.get());
        noteRepository.save(note.get());
        return true;
    }
}


