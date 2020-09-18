package com.fundoo.label.controller;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.service.ILabelService;
import com.fundoo.user.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/fundoo/label")
public class LabelController {

    @Autowired
    ILabelService labelService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createLabel(@Valid @RequestBody LabelDto labelDto, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        if (labelService.createLabel(labelDto, email))
            return new ResponseDto("Label Created", 201);
        return new ResponseDto("Error Creating label", 400);
    }

    @PostMapping(value = "/noteLabel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto mapLabel(@RequestBody LabelDto labelDto, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        if (labelService.mapLabel(labelDto, email))
            return new ResponseDto("Label Mapped Successfully", 200);
        return new ResponseDto("Label Not Present", 404);
    }

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto editLabel(@Valid @RequestBody LabelDto labelDto, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        if (labelService.editLabel(labelDto, email))
            return new ResponseDto("Label Edited Successfully", 201);
        return new ResponseDto("Error Editing label", 400);
    }

    @DeleteMapping(value = "/delete/{label_Id}")
    public ResponseDto deleteLabel(@PathVariable("label_Id") int note_id, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        if (labelService.deleteLabel(note_id, email))
            return new ResponseDto("Label Deleted", 202);
        return new ResponseDto("Error Deleting label", 400);
    }
}
