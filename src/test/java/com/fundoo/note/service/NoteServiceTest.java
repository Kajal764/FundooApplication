package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.ReminderDto;
import com.fundoo.note.dto.SortDto;
import com.fundoo.note.enumerations.GetNote;
import com.fundoo.note.enumerations.SortBaseOn;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
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
    void givenIdForNoteRestore_WhenRestoreNote_ItShouldReturnTrue() throws NoteException {
        note.setTrash(true);
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = noteService.restoreTrashNote(4);
        Assert.assertTrue(result);
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

    @Test
    void givenEmailToGetAllNote_WhenReturn_ItShouldReturnAllData() {
        String email = "kdw@gmail.com";
        List<Note> labelList = new ArrayList<>();
        labelList.add(note);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(user.getNoteList()).thenReturn(labelList);
        List<Note> notes = noteService.getNoteList(email);
        Assert.assertEquals(notes.size(), 1);
    }

    @Test
    void givenSortValueAndType_WhenSorting_ItShouldReturnSortedData() {
        SortDto sortDto = new SortDto(SortBaseOn.NAME, "asc");
        Note note1 = new Note();
        Note note2 = new Note();
        NoteDto noteDto1 = new NoteDto(4, "va", "this is desciption");
        NoteDto noteDto2 = new NoteDto(4, "spring", "this is desciption");
        BeanUtils.copyProperties(noteDto1, note1);
        BeanUtils.copyProperties(noteDto2, note2);
        List<Note> list = new ArrayList<>();
        list.add(note1);
        list.add(note2);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(user.getNoteList()).thenReturn(list);
        List<Note> sort = noteService.sort(sortDto, "kdw@gmail.com");
        Assert.assertTrue(sort.get(0).getTitle().contains("spring"));
        Assert.assertTrue(sort.get(1).getTitle().contains("va"));
    }

    @Test
    void givenNoteIdToPinNote_WhenNoteUnPin_ItShouldReturnTrue() throws NoteException {
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = noteService.pinUnpinNote(4, "kdw@gmail.com");
        assertThat(result, is(true));
    }

    @Test
    void givenNoteIdToUnPinNote_WhenNotePin_ItShouldReturnFalse() throws NoteException {
        note.setPin(true);
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = noteService.pinUnpinNote(4, "kdw@gmail.com");
        assertThat(result, is(false));

    }

    @Test
    void givenNoteIdToArchiveNote_WhenNoteUnArchive_ItShouldReturnTrue() throws NoteException {
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = noteService.archive(4, "kdw@gmail.com");
        assertThat(result, is(true));
    }

    @Test
    void givenNoteIdToUnArchiveNote_WhenNoteArchive_ItShouldReturnFalse() throws NoteException {
        note.setArchive(true);
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = noteService.archive(4, "kdw@gmail.com");
        assertThat(result, is(false));
    }


    @Test
    void givenRequestToTrashNote_WhenReturn_ItShouldReturnList() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        List<Note> list = new ArrayList<>();
        Note note1 = new Note();
        Note note2 = new Note();
        note1.setTitle("trashNote");
        note1.setTrash(true);
        note2.setTitle("Untrash note");
        list.add(note1);
        list.add(note2);
        when(user.getNoteList()).thenReturn(list);
        List<Note> notes = noteService.getNotes(GetNote.TRASH, "Kdw@Gmail.com");

        Assert.assertTrue(notes.get(0).getTitle().contains("trashNote"));
    }

    @Test
    void givenRequestToArchiveNote_WhenReturn_ItShouldReturnList() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        List<Note> list = new ArrayList<>();
        Note note1 = new Note();
        Note note2 = new Note();
        note1.setTitle("archievedNote");
        note1.setArchive(true);
        note2.setTitle("note");
        list.add(note1);
        list.add(note2);
        when(user.getNoteList()).thenReturn(list);
        List<Note> notes = noteService.getNotes(GetNote.ARCHIVE, "Kdw@Gmail.com");

        Assert.assertTrue(notes.get(0).getTitle().contains("archievedNote"));
    }

    @Test
    void givenReminder_WhenSetReminderItShouldReturnTrue() throws NoteException {
        LocalDateTime time = LocalDateTime.now();
        ReminderDto reminderDto = new ReminderDto(4, time);
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = noteService.setReminder(reminderDto, "kdw@gmail.com");
        Assert.assertEquals(result, true);
        Assert.assertEquals(note.getRemainder(), time);
        verify(noteRepository).save(note);
    }

    @Test
    void givenReminder_WhenDeleteReminderItShouldReturnTrue() throws NoteException {
        LocalDateTime time = LocalDateTime.now();
        ReminderDto reminderDto = new ReminderDto(4, time);
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = noteService.deleteReminder(reminderDto, "kdw@gmail.com");
        Assert.assertEquals(result, true);
        Assert.assertEquals(note.getRemainder(), null);
        verify(noteRepository).save(note);
    }
}
