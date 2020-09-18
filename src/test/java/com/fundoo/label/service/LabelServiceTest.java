package com.fundoo.label.service;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.exception.LabelException;
import com.fundoo.label.model.Label;
import com.fundoo.label.repository.LabelRepository;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LabelServiceTest {

    @InjectMocks
    LabelService labelService;

    @Mock
    UserRepository userRepository;

    @Mock
    LabelRepository labelRepository;

    @Mock
    INoteRepository noteRepository;

    @Mock
    List<Note> mockLabellist;

    @Mock
    Note note;

    @Mock
    User user;

    LabelDto labelDto;
    String email;
    Label label;

    @BeforeEach
    void setUp() {
        email = "kajaldw666@gmail.com";
        labelDto = new LabelDto(2, 5, "java");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        label = new Label();
        BeanUtils.copyProperties(labelDto, label);
    }

    @Test
    void givenLabelName_whenCreatingLabel_ItShouldReturnTrue() {
        when(labelRepository.findBylabelName(any())).thenReturn(Optional.empty());

        Assert.assertTrue(labelService.createLabel(labelDto, email));
    }

    @Test
    void givenLabelNameWhichPresent_whenCreatingLabel_ItShouldReturnThrowException() {
        when(labelRepository.findBylabelName(any())).thenReturn(Optional.of(label));

        assertThrows(LabelException.class, () -> labelService.createLabel(labelDto, email));
    }

    @Test
    void givenLabelNameWithNoteId_WhenMappingLabel_ItShouldReturnTrue() {
        mockLabellist = new ArrayList<>();
        label.setNoteList(mockLabellist);
        when(labelRepository.findBylabelName(any())).thenReturn(Optional.of(label));
        when(noteRepository.findById(anyInt())).thenReturn(Optional.of(note));
        boolean result = labelService.mapLabel(labelDto, email);

        assertThat(result, is(true));
    }

    @Test
    void givenLabelNameWithNoteId_WhenNoteNotPresent_ItShouldThrowException() {
        mockLabellist = new ArrayList<>();
        label.setNoteList(mockLabellist);
        when(labelRepository.findBylabelName(any())).thenReturn(Optional.of(label));
        when(noteRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LabelException.class, () -> labelService.mapLabel(labelDto, email));
        verify(labelRepository, never()).save(label);
    }

    @Test
    void givenNonExistLabelNameWithNoteId_WhenMapping_ItShouldReturnfalse() {
        mockLabellist = new ArrayList<>();
        label.setNoteList(mockLabellist);
        when(labelRepository.findBylabelName(any())).thenReturn(Optional.empty());
        boolean result = labelService.mapLabel(labelDto, email);

        assertThat(result, is(false));
        verifyNoInteractions(noteRepository);
        verify(labelRepository, never()).save(label);
    }

    @Test
    void givenLabelToEditWithLabelId_WhenEditLabel_ItShouldReturnTrue() {
        when(labelRepository.findById(anyInt())).thenReturn(Optional.of(label));
        boolean result = labelService.editLabel(labelDto, email);
        verify(labelRepository).save(label);
        assertThat(result, is(true));
    }

    @Test
    void givenLabelToEditWithLabelId_WhenEditLabelNotPresent_ItShouldReturnTrue() {
        when(labelRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(LabelException.class, () -> labelService.editLabel(labelDto, email));
        verify(labelRepository, never()).save(label);
    }

    @Test
    void givenLabelToDeleteWithLabelId_WhenDeleteLabel_ItShouldReturnTrue() {
        when(labelRepository.findById(anyInt())).thenReturn(Optional.of(label));
        boolean result = labelService.deleteLabel(4, email);
        verify(labelRepository).delete(label);
        assertThat(result, is(true));
    }

    @Test
    void givenLabelToDeleteWithLabelId_WhenLabelNotPresent_ItShouldReturnTrue() {
        when(labelRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(LabelException.class, () -> labelService.deleteLabel(4, email));
        verify(labelRepository, never()).delete(label);
    }

}