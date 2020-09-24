package com.fundoo.note.service;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.model.Note;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class IESServiceTest {

    @Autowired
    ESService esService;

    @Test
    void givenSearchValue_WhenReturn_ItShouldGiveList() throws IOException {
        List<Note> list = esService.searchByTitle("java", "kdw@gmail.com");
        Assert.assertTrue(list.get(0).getTitle().contains("java"));
    }

    @Test
    void givenNoteToSave_WhenSaveInIndex_ItShouldGiveIndexResult() throws IOException {
        NoteDto noteDto = new NoteDto(5, "Spring", "this is description");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);
        String result = esService.saveNote(note);
        Assert.assertEquals(result, "CREATED");
    }

    @Test
    void givenValueToDeleteNote_WhenDeleteInIndex_ItShouldGiveIndexResult() throws IOException {
        NoteDto noteDto = new NoteDto(3, "java", "here is update message");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);
        String result = esService.deleteNote(note);
        Assert.assertEquals(result, "DELETED");
    }
}
