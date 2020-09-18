package com.fundoo.label.controller;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.service.ILabelService;
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

    @Test
    void givenRequestToCreateLabel_WhenLabelCreate_ItShouldReturnStatusOK() throws Exception, NoteException {

        LabelDto labelDto = new LabelDto("java");
        String toJson = gson.toJson(labelDto);
        when(labelService.createLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(200));

    }

    @Test
    void givenRequestToCreateLabel_WhenLabelCreate_ItShouldReturnSuccessMessage() throws Exception, NoteException {

        LabelDto labelDto = new LabelDto("java");
        String toJson = gson.toJson(labelDto);
        when(labelService.createLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Created"));
    }

    @Test
    void givenRequestToCreateLabel_WhenLabelNotCreate_ItShouldReturnFailureMessage() throws Exception, NoteException {

        LabelDto labelDto = new LabelDto("java");
        String toJson = gson.toJson(labelDto);
        when(labelService.createLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Creating label"));
    }

}
