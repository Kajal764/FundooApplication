//package com.fundoo.user.controller;
//
//import com.fundoo.user.dto.ForgotPwDto;
//import com.fundoo.user.dto.LoginDto;
//import com.fundoo.user.dto.RegisterUserDto;
//import com.fundoo.user.dto.ResponseDto;
//import com.fundoo.user.model.User;
//import com.fundoo.user.service.UserService;
//import com.google.gson.Gson;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import javax.mail.MessagingException;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    UserService userService;
//
//    Gson gson = new Gson();
//    RegisterUserDto registerUserDto;
//    String toJson;
//
//    @BeforeEach
//    void setUp() throws MessagingException, UnsupportedEncodingException {
//        registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "Asha@se");
//        toJson = gson.toJson(registerUserDto);
//        when(userService.register(any())).thenReturn(new ResponseDto("Registration Successfull", 200));
//    }
//
//    @Test
//    void givenRequest_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
//        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/register").content(toJson)
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
//    }
//
//    @Test
//    void givenRequest_WhenGetResponse_ItShouldReturnSuccessMessage() throws Exception {
//        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/register").content(toJson)
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Registration Successful"));
//    }
//
//    @Test
//    void givenRequestWithAnotherMediaType_WhenGetResponse_ItShouldNotAccept() throws Exception {
//        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/register").content(toJson)
//                .contentType(MediaType.APPLICATION_PROBLEM_XML_VALUE)).andReturn();
//        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Unsupported Media Type"));
//    }
//
//    @Test
//    void givenRequestForLogin_WhenGetResponse_ItShouldReturnToken() throws Exception {
//
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
//        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
//        String toJson = gson.toJson(loginDto);
//        when(userService.login(any())).thenReturn(token);
//        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/login").content(toJson)
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Login Successful"));
//    }
//
//    @Test
//    void givenRequestToGetUnverifiedUser_WhenReturnItShouldReturnList() throws Exception {
//        List<User> userList = new ArrayList<>();
//        User user = new User();
//        user.setEmail("Kdw@gmail.com");
//        userList.add(user);
//        when(userService.unVerifyAccount()).thenReturn(userList);
//        MvcResult mvcResult = this.mockMvc.perform(get("/fundoo/user/unVerifiedUser")
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(user.getEmail()));
//    }
//
//    @Test
//    void givenRequestForForgotPw_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
//        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com");
//        String toJson = gson.toJson(forgotPwDto);
//        when(userService.checkDetails(any())).thenReturn(new ResponseDto("Otp Has been sent to your account", 200));
//        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/forgot_password").content(toJson)
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
//
//    }
//
//    @Test
//    void givenRequestForForgotPw_WhenGetResponse_ItShouldReturnCorrectSuccesMessage() throws Exception {
//        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com");
//        String toJson = gson.toJson(forgotPwDto);
//
//        when(userService.checkDetails(any())).thenReturn(new ResponseDto("Otp Has been sent to your account", 200));
//        MvcResult mvcResult = this.mockMvc.perform(post("/fundoo/user/forgot_password").content(toJson)
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Otp Has been sent to your account"));
//    }
//
//    @Test
//    void givenRequestForResetPassword_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
//        when(userService.redirectToUpdatePassword(any())).thenReturn("http://localhost:8080/fundoo/update/" + token);
//        MvcResult mvcResult = mockMvc.perform(get("/fundoo/user/reset_password/" + token)).andReturn();
//        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
//    }
//
//    @Test
//    void givenRequestForResetPassword_WhenGetResponse_ItShouldRedirectToUpadtePasswordApi() throws Exception {
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
//        when(userService.redirectToUpdatePassword(any())).thenReturn("http://localhost:8080/fundoo/update/" + token);
//        MvcResult mvcResult = mockMvc.perform(get("/fundoo/user/reset_password/" + token)).andReturn();
//        String updateApi = "http://localhost:8080/fundoo/update/eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
//        Assert.assertEquals(mvcResult.getResponse().getContentAsString(), updateApi);
//    }
//
//    @Test
//    void givenRequestWithToken_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
//        MvcResult mvcResult = mockMvc.perform(get("/fundoo/user/verify")
//                .param("token", token)).andReturn();
//        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
//    }
//
//    @Test
//    void givenRequest_WhenGetResponse_ItShouldReturnSuccessVerifyMessage() throws Exception {
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
//        when(userService.verifyUser(token)).thenReturn("Verify User Successfully");
//        MvcResult mvcResult = mockMvc.perform(get("/fundoo/user/verify")
//                .param("token", token)).andReturn();
//        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Verify User Successfully"));
//    }
//
//}
//
