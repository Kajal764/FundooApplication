package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.AuthenticationException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.dto.LoginDto;
import com.fundoo.user.dto.RegisterUserDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.service.RedisService;
import com.fundoo.user.utility.JwtUtil;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoteServiceTest {

    @InjectMocks
    NoteService noteService;

    @Mock
    INoteRepository noteRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    RedisService redisService;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    User user;


    @Test
    void givenTitleAndDesciption_whenCreatingNote_ItShouldreturnNoteData() {
        NoteDto noteDto = new NoteDto("java", "this is desciption");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);
        List<Note> mockList = new ArrayList<>();
        user.setNoteList(mockList);
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
        String exactToken = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
        when(jwtUtil.verify(any())).thenReturn("kajalw1998@gmail.com");
        when(redisService.getToken(any())).thenReturn(exactToken);
        when(noteRepository.save(any())).thenReturn(note);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        ResponseDto response = noteService.createNote(noteDto, token);
        Assert.assertEquals(response.message, "Note created successfully");
    }

    @Test
    void givenTitleAndDesciptionIfNotAuthenticate_whenCreatingNote_ItShouldThrowException() {
        NoteDto noteDto = new NoteDto("java", "this is desciption");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);
        List<Note> mockList = new ArrayList<>();
        user.setNoteList(mockList);
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
        String exactToken = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
        when(jwtUtil.verify(any())).thenReturn("kajalw1998@gmail.com");
        when(redisService.getToken(any())).thenReturn(exactToken);
        when(noteRepository.save(any())).thenReturn(note);
        when(userRepository.findByEmail(any())).thenReturn(null);
        try {
            noteService.createNote(noteDto, token);
        } catch (AuthenticationException e) {
            Assert.assertEquals(e.getMessage(), "Authentication Fail");
        }
    }



}
