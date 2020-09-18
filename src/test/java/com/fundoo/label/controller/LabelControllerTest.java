package com.fundoo.label.controller;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.service.ILabelService;
import com.fundoo.note.exception.NoteException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
public class LabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ILabelService labelService;

    Gson gson = new Gson();
    LabelDto labelDto;
    String toJson;

    @BeforeEach
    void setUp() {
        labelDto = new LabelDto(4, 3, "java");
        toJson = gson.toJson(labelDto);
    }

    @Test
    void givenRequestToCreateLabel_WhenLabelCreate_ItShouldReturnStatusOK() throws Exception, NoteException {
        when(labelService.createLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToCreateLabel_WhenLabelCreate_ItShouldReturnSuccessMessage() throws Exception, NoteException {
        when(labelService.createLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Created"));
    }

    @Test
    void givenRequestToCreateLabel_WhenLabelNotCreate_ItShouldReturnFailureMessage() throws Exception, NoteException {
        when(labelService.createLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Creating label"));
    }

    @Test
    void givenRequestToMapLabel_WhenLabelMapWithNote_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/noteLabel").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToMapLabel_WhenLabelMapWithNote_ItShouldReturnSuccesMessage() throws Exception {
        when(labelService.mapLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/noteLabel").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Mapped Successfully"));
    }

    @Test
    void givenRequestToMapLabel_WhenLabelNotMapWithNote_ItShouldReturnFailureMessage() throws Exception {
        when(labelService.mapLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/noteLabel").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Not Present"));
    }

    @Test
    void givenRequestToEditLabel_WhenEditLabel_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/fundoo/label/edit").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToEditLabel_WhenEditLabel_ItShouldReturnSuccessMessage() throws Exception {
        when(labelService.editLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(put("/fundoo/label/edit").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Edited Successfully"));
    }

    @Test
    void givenRequestToEditLabel_WhenNotAbleToEditLabel_ItShouldReturnFailureMessage() throws Exception {
        when(labelService.editLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(put("/fundoo/label/edit").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Editing label"));
    }

}
