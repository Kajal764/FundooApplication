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

    String token;
    String exactToken;
    NoteDto noteDto;
    Note note;
    List<Note> mockList;

//    @BeforeEach
//    void setUp() {
//        noteDto = new NoteDto("java", "this is desciption");
//        note = new Note();
//        BeanUtils.copyProperties(noteDto, note);
//
//        mockList = new ArrayList<>();
//        user.setNoteList(mockList);
//
//        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
//        exactToken = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
//        when(jwtUtil.verify(any())).thenReturn("kajalw1998@gmail.com");
//        when(redisService.getToken(any())).thenReturn(exactToken);
//
//    }
//
//
//    @Test
//    void givenTitleAndDesciption_whenCreatingNote_ItShouldreturnNoteData() {
//        when(noteRepository.save(any())).thenReturn(note);
//        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
//        ResponseDto response = noteService.createNote(noteDto, token);
//        Assert.assertEquals(response.message, "Note created successfully");
//    }
//
//    @Test
//    void givenTitleAndDesciptionIfNotAuthenticate_whenCreatingNote_ItShouldThrowException() {
//
//        when(noteRepository.save(any())).thenReturn(note);
//        when(userRepository.findByEmail(any())).thenReturn(null);
//        try {
//            noteService.createNote(noteDto, token);
//        } catch (AuthenticationException e) {
//            Assert.assertEquals(e.getMessage(), "User Don't have permission");
//        }
//    }
//
//
//    @Test
//    void givenIdForNoteTrash_whenTrashNote_ItShouldReturnSuccessMessage() throws NoteException {
//        int note_id = 4;
//        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
//        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
//        ResponseDto trash = noteService.deleteNote(note_id, token);
//        Assert.assertEquals(trash.message, "Note trashed");
//    }
//
//    @Test
//    void givenIdForNoteTrash_whenUserNotAuthenticate_ItShouldThrowException() throws NoteException {
//        int note_id = 4;
//        when(userRepository.findByEmail(any())).thenReturn(null);
//        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
//        try {
//            noteService.deleteNote(note_id, token);
//        } catch (AuthenticationException e) {
//            Assert.assertEquals(e.getMessage(), "User Don't have permission");
//        }
//    }
//
//    @Test
//    void givenIdForNoteDelete_whenDeleteNote_ItShouldReturnSuccessMessage() throws NoteException {
//        int note_id = 4;
//        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
//        note.setTrash(true);
//        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
//        ResponseDto trash = noteService.deleteNote(note_id, token);
//        Assert.assertEquals(trash.message, "Note trashed");
//    }
//
//    @Test
//    void givenIdForNoteTrash_whenNoteIsNotInTrash_ItShouldThrowException() throws NoteException {
//        int note_id = 4;
//        note.setTrash(false);
//        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
//        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
//        try {
//            noteService.trashNoteDelete(note_id,token);
//        } catch (NoteException e) {
//            Assert.assertEquals(e.getMessage(), "Note is not in trash");
//        }
//    }
//
//    @Test
//    void givenIdForNoteDelete_whenUserNotAuthenticate_ItShouldThrowException() throws NoteException {
//        int note_id = 4;
//        when(userRepository.findByEmail(any())).thenReturn(null);
//        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
//        try {
//            noteService.deleteNote(note_id, token);
//        } catch (AuthenticationException e) {
//            Assert.assertEquals(e.getMessage(), "User Don't have permission");
//        }
//    }

}
