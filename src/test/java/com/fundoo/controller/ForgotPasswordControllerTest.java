package com.fundoo.controller;

import com.fundoo.dto.ForgotPwDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.service.ForgotPWService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
public class ForgotPasswordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ForgotPWService forgotPWService;

    Gson gson = new Gson();

    @Test
    void givenRequestForForgotPw_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com", "Guddi", "waghmare");
        String toJson = gson.toJson(forgotPwDto);
        when(forgotPWService.checkDetails(any())).thenReturn(new ResponseDto("Otp Has been sent to your account", 200));
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/forgotpassword").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);

    }

    @Test
    void givenRequestForForgotPw_WhenGetResponse_ItShouldReturnCorrectSuccesMessage() throws Exception {
        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com", "Guddi", "waghmare");
        String toJson = gson.toJson(forgotPwDto);

        when(forgotPWService.checkDetails(any())).thenReturn(new ResponseDto("Otp Has been sent to your account", 200));
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/forgotpassword").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Otp Has been sent to your account"));

    }
}
