package com.fundoo.note.controller;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.exception.NoteException;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    INoteService noteService;

    Gson gson = new Gson();

    @Test
    void givenRequest_whenCreatingNote_ItShouldReturnStatusCreated() throws Exception {
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
    void givenRequest_whenCreatingNote_ItShouldReturnNoteData() throws Exception {
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

    @Test
    void givenRequestToDelete_whenNoteAddInTrash_ItShouldReturnStatusOk() throws Exception, NoteException {

        when(noteService.deleteNote(anyInt(),anyString())).thenReturn(new ResponseDto("Note trashed", 200));
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/delete/5")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(),200);
    }

//    @Test
//    void givenRequestToDelete_whenNoteAddInTrash_ItShouldReturnSuccessMessage() throws Exception, NoteException {
//        ResponseDto response = new ResponseDto("Note trashed", 200);
//        System.out.println("teset  "+response);
//        when(noteService.deleteNote(anyInt(),anyString())).thenReturn(response);
//       // when(noteService.deleteNote(anyInt(),anyString())).thenReturn(note_created_successfully);
////        when(noteService.trash(anyInt(),anyString())).thenReturn(note_created_successfully);
//        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/delete/4")).andReturn();
//        System.out.println("dsjbhcds"+mvcResult.getResponse().getContentAsString());
//    }


    @Test
    void givenRequestToDeleteNotePermanently_WhenNotIsInTrash_ItShouldReturnStatusOk() throws Exception, NoteException {

        when(noteService.trashNoteDelete(anyInt(),anyString())).thenReturn(new ResponseDto("Note Deleted Successfully", 200));
        MvcResult mvcResult = this.mockMvc.perform(delete("/fundoo/note/trash/5")).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(),200);

    }
}
