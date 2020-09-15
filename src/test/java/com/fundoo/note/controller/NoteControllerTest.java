package com.fundoo.note.controller;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.model.Note;
import com.fundoo.note.service.INoteService;
import com.fundoo.user.dto.ResponseDto;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    INoteService noteService;

    Gson gson = new Gson();

    @Test
    void givenRequest_whenCreatingNote_ItShouldRerurnStatusCreated() throws Exception {
        NoteDto noteDto = new NoteDto("java", "this is desciption");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto,note);
        String toJson = gson.toJson(noteDto);
        when(noteService.createNote(any(), any())).thenReturn(new ResponseDto("Note created successfully", 201));

        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/note/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(),200);
    }

    @Test
    void givenRequest_whenCreatingNote_ItShouldRerurnNoteData() throws Exception {
        NoteDto noteDto = new NoteDto("java", "this is desciption");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto,note);
        note.setCreatedDate(LocalDateTime.now());
        String toJson = gson.toJson(noteDto);
        when(noteService.createNote(any(), any())).thenReturn(new ResponseDto("Note created successfully", 201));

        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/note/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Note created successfully"));
    }
}
