package com.fundoo.note.controller;


import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.service.INoteService;
import com.fundoo.user.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fundoo/note")
public class NoteController {

    @Autowired
    INoteService noteService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createNote(@RequestBody NoteDto noteDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
       return noteService.createNote(noteDto, token);
    }
}

