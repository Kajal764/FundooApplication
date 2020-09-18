package com.fundoo.note.service;

import com.fundoo.label.model.Label;
import com.fundoo.label.repository.LabelRepository;
import com.fundoo.note.dto.NoteDto;
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

import java.time.LocalDateTime;
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

    @Autowired
    LabelRepository labelRepository;

    @Override
    public ResponseDto createNote(NoteDto noteDto, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Note newNote = new Note();
            BeanUtils.copyProperties(noteDto, newNote);
            user.get().getNoteList().add(newNote);
            noteRepository.save(newNote);
            return new ResponseDto("Note created successfully", 200);
        }
        return new ResponseDto("User not present", 403);
    }

    @Override
    public ResponseDto deleteNote(int note_id, String email) throws NoteException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user != null) {
            Optional<Note> note = noteRepository.findById(note_id);
            if (note.isPresent()) {
                note.get().setTrash(true);
                noteRepository.save(note.get());
                return new ResponseDto("Note trashed", 200);
            }
            throw new NoteException("Note is not present");
        }
        return new ResponseDto("User not present", 403);
    }

    @Override
    public ResponseDto trashNoteDelete(int note_id, String email) throws NoteException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Optional<Note> note = noteRepository.findById(note_id);

            if (note.isPresent() && note.get().isTrash() == true) {
                noteRepository.delete(note.get());
                return new ResponseDto("Note Deleted Successfully", 200);
            }
            throw new NoteException("Note is not in trash");
        }
        return new ResponseDto("User not present", 403);
    }

    @Override
    public boolean updateNote(NoteDto noteDto, String email) throws NoteException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Optional<Note> note = noteRepository.findById(noteDto.note_id);
            return note.map((value) -> {
                value.setTitle(noteDto.title);
                value.setDescription(noteDto.description);
                value.setEditDate(LocalDateTime.now());
                noteRepository.save(value);
                return true;
            }).orElseThrow(() -> new NoteException("Note Is Not Present"));
        }
        return false;
    }
}
