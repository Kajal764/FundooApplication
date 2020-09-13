package com.fundoo.controller;

import com.fundoo.service.RegistrationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenVerificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegistrationService registrationService;

    @Test
    void givenRequest_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/verifyUser")
                .param("token", token)).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void givenRequest_WhenGetResponse_ItShouldReturnSuccessVerifyMessage() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        when(registrationService.verifyUser(token)).thenReturn("Verify User Successfully");
        MvcResult mvcResult = mockMvc.perform(get("/fundoo/verifyUser")
                .param("token", token)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Verify User Successfully"));
    }
}
