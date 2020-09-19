package com.fundoo.user.controller;

import com.fundoo.user.dto.ForgotPwDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.service.ForgotPWService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com");
        String toJson = gson.toJson(forgotPwDto);
        when(forgotPWService.checkDetails(any())).thenReturn(new ResponseDto("Otp Has been sent to your account", 200));
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/forgot_password").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);

    }

    @Test
    void givenRequestForForgotPw_WhenGetResponse_ItShouldReturnCorrectSuccesMessage() throws Exception {
        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com");
        String toJson = gson.toJson(forgotPwDto);

        when(forgotPWService.checkDetails(any())).thenReturn(new ResponseDto("Otp Has been sent to your account", 200));
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/forgot_password").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Otp Has been sent to your account"));
    }

    @Test
    void givenRequestForResetPassword_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        when(forgotPWService.redirectToUpdatePassword(any())).thenReturn("http://localhost:8080/fundoo/update/" + token);
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/user/reset_password/" + token)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestForResetPassword_WhenGetResponse_ItShouldRedirectToUpadtePasswordApi() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        when(forgotPWService.redirectToUpdatePassword(any())).thenReturn("http://localhost:8080/fundoo/update/" + token);
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/user/reset_password/" + token)).andReturn();
        String updateApi = "http://localhost:8080/fundoo/update/eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        Assert.assertEquals(mvcResult.getResponse().getContentAsString(), updateApi);
    }

}
