package com.fundoo.collaborator.service;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CollaboratorServiceTest {

    @InjectMocks
    CollaborateService collaboratorService;

    @Mock
    UserRepository userRepository;

    @Mock
    INoteRepository noteRepository;

    @Mock
    Note note;

    @Mock
    User user;

    @Test
    void givenNoteIdAndEmail_WhenCollaborateItShouldReturnTrue() throws NoteException {
        CollaborateNoteDto collaborateNoteDto = new CollaborateNoteDto(3, "kdw@gmail.com");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = collaboratorService.addCollaborator(collaborateNoteDto, "kdw@gmail.com");
        verify(noteRepository).save(note);
        Assert.assertEquals(result, true);
    }

    @Test
    void whenReturnCollaborateNote_ItShouldReturnList() throws NoteException {
        Note note = new Note();
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(user.getCollaborateNotes()).thenReturn(notes);
        List<Note> collaboratorNotes = collaboratorService.getCollaboratorNotes("kdw@gmail.com");
        Assert.assertEquals(collaboratorNotes.size(), 1);
    }
}
