package com.fundoo.note.controller;

import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.SortDto;
import com.fundoo.note.enumerations.SortBaseOn;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.service.IElasticSearchService;
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
import java.util.ArrayList;
import java.util.List;

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

    @MockBean
    IElasticSearchService IElasticSearchService;

    Gson gson = new Gson();


    @Test
    void givenRequest_whenCreatingNote_ItShouldReturnStatusCreated() throws Exception {
        NoteDto noteDto = new NoteDto(5, "java", "this is desciption");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);
        String toJson = gson.toJson(noteDto);
        when(noteService.createNote(any(), any())).thenReturn(new ResponseDto("Note created successfully", 201));

        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/note/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequest_whenCreatingNote_ItShouldReturnNoteData() throws Exception {
        NoteDto noteDto = new NoteDto(2, "java", "this is desciption");
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);
        note.setCreatedDate(LocalDateTime.now());
        String toJson = gson.toJson(noteDto);
        when(noteService.createNote(any(), any())).thenReturn(new ResponseDto("Note created successfully", 201));

        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/note/create").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Note created successfully"));
    }

    @Test
    void givenRequestToDelete_whenNoteAddInTrash_ItShouldReturnStatusOk() throws Exception, NoteException {

        when(noteService.deleteNote(anyInt(), anyString())).thenReturn(new ResponseDto("Note trashed", 200));
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/delete/5")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestToRestore_WhenNoteIsInTrash_ItShouldReturnSuccessMessage() throws Exception, NoteException {

        when(noteService.restoreTrashNote(anyInt())).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/restoreTrashNote/43")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Note Restored"));
    }

    @Test
    void givenRequestToDeleteNotePermanently_WhenNotIsInTrash_ItShouldReturnStatusOk() throws Exception, NoteException {
        when(noteService.trashNoteDelete(anyInt(), anyString())).thenReturn(new ResponseDto("Note Deleted Successfully", 200));
        MvcResult mvcResult = this.mockMvc.perform(delete("/fundoo/note/trash/5")).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestToUpdateNote_WhenNoteUpdate_ItShouldReturnStatusOk() throws Exception, NoteException {
        NoteDto noteDto = new NoteDto(2, "java", "this is desciption");
        String toJson = gson.toJson(noteDto);
        when(noteService.updateNote(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(put("/fundoo/note/update").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestToUpdateNote_WhenNoteUpdate_ItShouldReturnSuccessMessage() throws Exception, NoteException {

        NoteDto noteDto = new NoteDto(2, "java", "this is desciption");
        String toJson = gson.toJson(noteDto);
        when(noteService.updateNote(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(put("/fundoo/note/update").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Note Updated"));
    }


    @Test
    void givenDifferentMethodToUpdateNote_WhenNoteUpdate_ItShouldReturnMethodNotSupportedMessage() throws Exception, NoteException {

        NoteDto noteDto = new NoteDto(2, "java", "this is desciption");
        String toJson = gson.toJson(noteDto);
        when(noteService.updateNote(any(), any())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/note/update").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Request method 'GET' not supported"));
    }

    @Test
    void givenRequestToUpdateNote_WhenNoteNotUpdate_ItShouldReturnErrorMessage() throws Exception, NoteException {

        NoteDto noteDto = new NoteDto(2, "java", "this is desciption");
        String toJson = gson.toJson(noteDto);
        when(noteService.updateNote(any(), any())).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(put("/fundoo/note/update").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Error Updating Note"));
    }

    @Test
    void givenRequestToGetAllNote_WhenNoteReturn_ItShouldReturnList() throws Exception {
        Note note = new Note();
        NoteDto noteDto = new NoteDto(2, "java", "this is desciption");
        BeanUtils.copyProperties(noteDto, note);
        List<Note> labelList = new ArrayList<>();
        labelList.add(note);
        when(noteService.getNoteList(any())).thenReturn(labelList);
        MvcResult mvcResult = mockMvc.perform((get("/fundoo/note/list")).contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(note.getTitle()));
    }

    @Test
    void givenRequestToGetSorting_WhenReturnDataItShouldReturnInSortedOrder() throws Exception {
        SortDto sortDto = new SortDto(SortBaseOn.NAME, "asc");
        String jsonDto = gson.toJson(sortDto);
        Note note1 = new Note();
        Note note2 = new Note();
        NoteDto noteDto1 = new NoteDto(4, "java", "this is desciption");
        NoteDto noteDto2 = new NoteDto(4, "spring", "this is desciption");
        BeanUtils.copyProperties(noteDto1, note1);
        BeanUtils.copyProperties(noteDto2, note2);
        List<Note> list = new ArrayList<>();
        list.add(note1);
        list.add(note2);

        when(noteService.sort(any(), any())).thenReturn(list);
        MvcResult mvcResult = mockMvc.perform(post("/fundoo/note/sort").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(note1.getTitle()));
    }


    @Test
    void givenRequestToPinNote_WhenNotePin_ItShouldReturnStatusOk() throws Exception, NoteException {
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/pinUnpin/5")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestToPinNote_WhenNoteUnPin_ItShouldReturnNotePinMessage() throws Exception, NoteException {
        when(noteService.pinUnpinNote(anyInt(), anyString())).thenReturn(false);
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/pinUnpin/5")).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Note Unpin"));
    }

    @Test
    void givenRequestToArchiveNote_WhenNoteArchive_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/archive/5")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestToUnArchiveNote_WhenNoteArchive_ItShouldReturnUNArchiveMessage() throws Exception, NoteException {
        when(noteService.archive(anyInt(), any())).thenReturn(false);
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/archive/5")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Note Unarchived"));
    }

    @Test
    void givenRequestToArchiveNote_WhenNoteUnArchive_ItShouldReturnArchiveMessage() throws Exception, NoteException {
        when(noteService.archive(anyInt(), any())).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(put("/fundoo/note/archive/5")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Note Archived"));
    }

    @Test
    void givenRequestToGetTrashNote_WhenReturnNote_ItShouldReturnTrashNote() throws Exception {
        Note note1 = new Note();
        List<Note> list = new ArrayList<>();
        note1.setTitle("first");
        note1.setTrash(true);
        list.add(note1);
        when(noteService.getNotes(any(), any())).thenReturn(list);
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/note/trashList")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(list.get(0).getTitle()));
    }

    @Test
    void givenRequest_WhenGetReminderNotes_ItShouldReturnNotes() throws Exception {
        Note note1 = new Note();
        List<Note> list = new ArrayList<>();
        note1.setTitle("reminder Note");
        note1.setRemainder(LocalDateTime.now());
        list.add(note1);
        when(noteService.getReminderSetNotes()).thenReturn(list);
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/note/reminder").contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(list.get(0).getTitle()));
    }


    @Test
    void givenRequestToSearchNoteBaseOnTitleOrDescription_ThenReturnSearchList() throws Exception {
        Note note1 = new Note();
        List<Note> list = new ArrayList<>();
        note1.setTitle("Note");
        list.add(note1);
        when(IElasticSearchService.searchByTitle(any(), any())).thenReturn(list);
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/note/searchData/here")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(note1.getTitle()));
    }
}
