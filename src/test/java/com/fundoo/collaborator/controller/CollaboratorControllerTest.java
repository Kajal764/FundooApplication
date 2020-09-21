package com.fundoo.collaborator.controller;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.collaborator.service.ICollaborateService;
import com.fundoo.note.exception.NoteException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class CollaboratorControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    ICollaborateService collaborateService;
    Gson gson = new Gson();

    @Test
    void givenRequestToCollaborateNote_WhenCollaborate_ItShouldReturnStatusOK() throws Exception, NoteException {

        CollaborateNoteDto collaborateNoteDto = new CollaborateNoteDto(3, "kdw@gmail.com");
        String toJson = gson.toJson(collaborateNoteDto);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/note/collaborators").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestToCollaborateNote_WhenCollaborate_ItShouldReturnSuccessMessage() throws Exception, NoteException {

        CollaborateNoteDto collaborateNoteDto = new CollaborateNoteDto(3, "kdw@gmail.com");
        String toJson = gson.toJson(collaborateNoteDto);
        when(collaborateService.addCollaborator(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/note/collaborators").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Collaborated User Successfully"));
    }

    @Test
    void givenRequestToRemoveCollaborateNote_WhenCollaborate_ItShouldReturnSuccessMessage() throws Exception, NoteException {

        CollaborateNoteDto collaborateNoteDto = new CollaborateNoteDto(3, "kdw@gmail.com");
        String toJson = gson.toJson(collaborateNoteDto);
        when(collaborateService.removeCollaboration(any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(delete("/fundoo/note/removeCollaborate").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Collaboration remove Successfully"));
    }
}
