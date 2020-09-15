package com.fundoo.user.controller;

import com.fundoo.user.dto.LoginDto;
import com.fundoo.user.service.LoginService;
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

@AutoConfigureMockMvc
@SpringBootTest
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoginService loginService;

    Gson gson = new Gson();
    LoginDto loginDto;
    String toJson;
    String token;


    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        toJson = gson.toJson(loginDto);
        when(loginService.login(any())).thenReturn(token);
    }

    @Test
    void givenRequestForLogin_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/login").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequestForLogin_WhenGetResponse_ItShouldReturnToken() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/login").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(token));
    }
}
