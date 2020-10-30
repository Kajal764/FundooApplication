package com.fundoo.collaborator.service;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import com.fundoo.user.utility.template.CollaborationInvitationTemplate;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
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

    @Mock
    CollaborationInvitationTemplate template;

    @Mock
    JavaMailUtil javaMailUtil;

    @Test
    void givenNoteIdAndEmail_WhenCollaborate_ItShouldReturnTrue() throws NoteException, MessagingException {
        CollaborateNoteDto collaborateNoteDto = new CollaborateNoteDto(3, "kdw@gmail.com");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        when(template.getHeader(any(),any())).thenReturn("");
        when(javaMailUtil.sendCollaborationInvite(any(),any(),any())).thenReturn("Collaborate user successfully");
        boolean result = collaboratorService.addCollaborator(collaborateNoteDto, "kdw@gmail.com");
        verify(noteRepository).save(note);
        Assert.assertEquals(result, true);
    }

    @Test
    void whenReturnCollaborateNote_ItShouldReturnList() throws NoteException {
        Note note = new Note();
        note.setCollaborateNote(true);
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(user.getNoteList()).thenReturn(notes);
        List<Note> collaboratorNotes = collaboratorService.getCollaboratorNotes("kdw@gmail.com");
        Assert.assertEquals(collaboratorNotes.size(), 1);
    }

}
