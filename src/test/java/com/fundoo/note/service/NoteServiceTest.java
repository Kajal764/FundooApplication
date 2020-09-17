package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
    User user;

    String token;
    String exactToken;
    NoteDto noteDto;
    Note note;
    List<Note> mockList;

    @BeforeEach
    void setUp() {

        noteDto = new NoteDto(3, "java", "this is description");

        note = new Note();
        BeanUtils.copyProperties(noteDto, note);

        mockList = new ArrayList<>();
        user.setNoteList(mockList);

        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
        exactToken = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNjAwMTU1NTY0LCJleHAiOjE2MDAxNTkxNjR9.oBgxhHCmIlWEVB-07aG1-e0NpsBpWlAHwSSrlaxblYReJCWAWmWV7HhZ3OZHf6E_zxFea9Omj0C4YlC9VaqDAw";
    }


    @Test
    void givenTitleAndDesciption_whenCreatingNote_ItShouldreturnNoteData() {
        when(noteRepository.save(any())).thenReturn(note);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        ResponseDto response = noteService.createNote(noteDto, token);
        Assert.assertEquals(response.message, "Note created successfully");
    }

    @Test
    void givenIdForNoteTrash_whenTrashNote_ItShouldReturnSuccessMessage() throws NoteException {
        int note_id = 4;
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
        ResponseDto trash = noteService.deleteNote(note_id, token);
        Assert.assertEquals(trash.message, "Note trashed");
    }

    @Test
    void givenIdForNoteTrash_whenUserNotAuthenticate_ItShouldThrowException() throws NoteException {
        int note_id = 4;
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
        ResponseDto responseDto = noteService.deleteNote(note_id, token);
        Assert.assertEquals(responseDto.message, "User not present");
    }

    @Test
    void givenIdForNoteDelete_whenDeleteNote_ItShouldReturnSuccessMessage() throws NoteException {
        int note_id = 4;
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        note.setTrash(true);
        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
        ResponseDto trash = noteService.deleteNote(note_id, token);
        Assert.assertEquals(trash.message, "Note trashed");
    }

    @Test
    void givenIdForNoteTrash_whenNoteIsNotInTrash_ItShouldThrowException() {
        int note_id = 4;
        note.setTrash(false);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
        try {
            noteService.trashNoteDelete(note_id, token);
            noteService.trashNoteDelete(note_id, token);
        } catch (NoteException e) {
            Assert.assertEquals(e.getMessage(), "Note is not in trash");
        }
    }

    @Test
    void givenIdForNoteDelete_whenUserNotAuthenticate_ItShouldThrowException() throws NoteException {
        int note_id = 4;
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(noteRepository.findById(4)).thenReturn(Optional.of(note));
        ResponseDto responseDto = noteService.deleteNote(note_id, token);
        Assert.assertEquals(responseDto.message, "User not present");
    }

    @Test
    void givenNoteId_WhenUpdateNote_ItShouldReturnUpdateNote() throws NoteException {
        NoteDto noteDto = new NoteDto(4, "java", "this is desciption");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);
        Assert.assertTrue(noteService.updateNote(noteDto, token));

    }

    @Test
    void givenNoteId_WhenUpdateNoteNotPresent_ItShouldReturnUpdateNote() {
        NoteDto noteDto = new NoteDto(4, "java", "this is desciption");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(noteRepository.findById(anyInt())).thenReturn(Optional.empty());

        NoteException noteException = assertThrows(NoteException.class, () -> noteService.updateNote(noteDto, token));
        assertThat(noteException.getMessage(), is("Note Is Not Present"));
    }

}
