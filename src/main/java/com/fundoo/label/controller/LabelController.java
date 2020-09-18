package com.fundoo.label.controller;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.service.ILabelService;
import com.fundoo.user.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/fundoo/label")
public class LabelController {

    @Autowired
    ILabelService labelService;

    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createLabel(@Valid @RequestBody LabelDto labelDto,HttpServletRequest request)  {
        String email = (String) request.getAttribute("email");
        if(labelService.createLabel(labelDto,email))
            return new ResponseDto("Label Created",201);
        return new ResponseDto("Error Creating label",400);
    }
}
