package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.AuthenticationException;
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

    private Optional<User> checkAuthentication(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            Object email = jwtUtil.verify(jwtToken);
            Optional<User> user = userRepository.findByEmail(email.toString());
            return user;
        }
        return null;
    }

    public ResponseDto createNote(NoteDto noteDto, String authorizationHeader) {

        Optional<User> user = checkAuthentication(authorizationHeader);
        if (user != null)
        {
        try {
            if (redisService.getToken(user.get().getEmail()) != null)
                {
                Note newNote = new Note();
                BeanUtils.copyProperties(noteDto, newNote);
                user.get().getNoteList().add(newNote);
                noteRepository.save(newNote);
                return new ResponseDto("Note created successfully", 200);
                }
            } catch (NoSuchElementException e) {
                throw new AuthenticationException("No Such E Found");
                }
        }
        throw new AuthenticationException("Authentication Fail");
    }
}