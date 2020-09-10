package com.fundoo.controller;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.service.RegistrationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegistrationService registrationService;

    Gson gson = new Gson();
    RegisterUserDto registerUserDto;
    String toJson;

    @BeforeEach
    void setUp() {
        registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        toJson = gson.toJson(registerUserDto);
        when(registrationService.register(any())).thenReturn("Registration Successfull");
    }

    @Test
    void givenRequest_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/register").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequest_WhenGetResponse_ItShouldReturnSuccessMessage() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/register").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getContentAsString(), "Registration Successfull");
    }

    @Test
    void givenRequestWithAnotherMediaType_WhenGetResponse_ItShouldNotAccept() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/register").content(toJson)
                .contentType(MediaType.APPLICATION_PROBLEM_XML_VALUE)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getContentAsString(), "Unsupported Media Type");
    }
}
