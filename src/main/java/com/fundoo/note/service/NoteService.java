package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.AuthenticationException;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.service.RedisService;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
class NoteService implements INoteService {

    @Autowired
    INoteRepository noteRepository;

    @Autowired
    JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;

    private Optional<User> checkUserWithEmailId(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            Object email = jwtUtil.verify(jwtToken);
            Optional<User> user = userRepository.findByEmail(email.toString());
            return user;
        }
        return null;
    }

    private boolean checkAuthorization(Optional<User> user) {
        if (user != null) {
            try {
                redisService.getToken(user.get().getEmail());
                return true;
            } catch (NoSuchElementException e) {
                throw new AuthenticationException("Authentication Fail");
            }
        }
        return false;
    }

    public ResponseDto createNote(NoteDto noteDto, String authorizationHeader) {

        Optional<User> user = checkUserWithEmailId(authorizationHeader);
        if (checkAuthorization(user)) {
            Note newNote = new Note();
            BeanUtils.copyProperties(noteDto, newNote);
            user.get().getNoteList().add(newNote);
            noteRepository.save(newNote);
            return new ResponseDto("Note created successfully", 200);
        }
        throw new AuthenticationException("User Don't have permission");
    }


    @Override
    public ResponseDto deleteNote(int note_id, String token) throws NoteException {
        Optional<User> user = checkUserWithEmailId(token);
        if (checkAuthorization(user)) {
            Optional<Note> note = noteRepository.findById(note_id);
            if (note.isPresent()) {
                note.get().setTrash(true);
                noteRepository.save(note.get());
                return new ResponseDto("Note trashed", 200);
            }
            throw new NoteException("Note is not present");
        }
        throw new AuthenticationException("User Don't have permission");
    }

    @Override
    public ResponseDto trashNoteDelete(int note_id, String token) throws NoteException {
        Optional<User> user = checkUserWithEmailId(token);
        if (checkAuthorization(user)) {
            Optional<Note> note = noteRepository.findById(note_id);
            if (note.isPresent() && note.get().isTrash() == true) {
                noteRepository.delete(note.get());
                return new ResponseDto("Note Deleted Successfully", 200);
            }
            throw new NoteException("Note is not in trash");
        }
        throw new AuthenticationException("User Don't have permission");
    }

    @Override
    public ResponseDto updateNote(int noteId, String token) {
        return null;
    }
}