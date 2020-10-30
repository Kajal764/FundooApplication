package com.fundoo.label.controller;

import com.fundoo.configuration.NoteServiceInterceptorAppConfig;
import com.fundoo.intercepter.NoteServiceInterceptor;
import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.dto.MapDto;
import com.fundoo.label.model.Label;
import com.fundoo.label.service.ILabelService;
import com.fundoo.note.exception.NoteException;
import com.fundoo.properties.FileProperties;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(LabelController.class)
public class LabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ILabelService labelService;

    @MockBean
    FileProperties fileProperties;

    @MockBean
    NoteServiceInterceptor noteServiceInterceptor;

    @MockBean
    NoteServiceInterceptorAppConfig noteServiceInterceptorAppConfig;

    Gson gson = new Gson();
    LabelDto labelDto;
    String labelJson;
    MapDto mapDto;
    String mapJson;

    @BeforeEach
    void setUp() {
        labelDto = new LabelDto(4, 3, "java");
        labelJson = gson.toJson(labelDto);
        mapDto = new MapDto(4, 6);
        mapJson = gson.toJson(mapDto);
    }

    @Test
    void givenRequestToCreateLabel_WhenLabelCreate_ItShouldReturnStatusOK() throws Exception {
        when(labelService.createLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToCreateLabel_WhenLabelCreate_ItShouldReturnSuccessMessage() throws Exception {
        when(labelService.createLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Created"));
    }

    @Test
    void givenRequestToCreateLabel_WhenLabelNotCreate_ItShouldReturnFailureMessage() throws Exception, NoteException {
        when(labelService.createLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/create").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Creating label"));
    }

    @Test
    void givenRequestToMapLabel_WhenLabelMapWithNote_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/noteLabel").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToMapLabel_WhenLabelMapWithNote_ItShouldReturnSuccesMessage() throws Exception {
        when(labelService.mapLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/noteLabel").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Mapped Successfully"));
    }

    @Test
    void givenRequestToMapLabel_WhenLabelNotMapWithNote_ItShouldReturnFailureMessage() throws Exception {
        when(labelService.mapLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/noteLabel").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Already Map"));
    }

    @Test
    void givenRequestToEditLabel_WhenEditLabel_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/edit").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToEditLabel_WhenEditLabel_ItShouldReturnSuccessMessage() throws Exception {
        when(labelService.editLabel(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/edit").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Edited Successfully"));
    }

    @Test
    void givenRequestToEditLabel_WhenNotAbleToEditLabel_ItShouldReturnFailureMessage() throws Exception {
        when(labelService.editLabel(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/edit").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Editing label"));
    }

    @Test
    void givenRequestToDeleteLabel_WhenDeleteLabel_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/fundoo/label/delete/5").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToDeleteLabel_WhenDeleteLabel_ItShouldReturnSuccessMessage() throws Exception {
        when(labelService.deleteLabel(anyInt(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(delete("/fundoo/label/delete/5").content(labelJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Deleted"));
    }

    @Test
    void givenRequestToDeleteLabel_WhenDeleteLabelNotPresent_ItShouldReturnFailureMessage() throws Exception {
        when(labelService.deleteLabel(anyInt(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(delete("/fundoo/label/delete/5").content(labelJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Deleting label"));
    }

    @Test
    void givenRequestToDeleteLabelWithAnotheHttpMethod_WhenDeleteLabel_ItShouldReturnMethodNotSupportMessage() throws Exception {
        when(labelService.deleteLabel(anyInt(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/delete/5").content(labelJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Request method 'POST' not supported"));
    }

    @Test
    void givenRequestToRemoveLabel_WhenRemoveLabel_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/removeLabel").content(mapJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));
    }

    @Test
    void givenRequestToRemoveLabel_WhenRemoveLabel_ItShouldReturnSuccessMessage() throws Exception {
        when(labelService.removeNoteLabel(any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/removeLabel").content(mapJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Label Remove"));
    }

    @Test
    void givenRequestToRemoveLabel_WhenLabelNotRemove_ItShouldReturnFailureMessage() throws Exception {
        when(labelService.removeNoteLabel(any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/label/removeLabel").content(mapJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Removing label"));
    }

    @Test
    void givenRequestToGetAllLabel_WhenLabelReturn_ItShouldReturnList() throws Exception {
        Label label = new Label();
        BeanUtils.copyProperties(labelDto, label);
        List<Label> labelList = new ArrayList<>();
        labelList.add(label);
        when(labelService.getLabelList(any())).thenReturn(labelList);
        MvcResult mvcResult = mockMvc.perform((get("/fundoo/label/list")).contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(label.getLabelName()));

    }
}
