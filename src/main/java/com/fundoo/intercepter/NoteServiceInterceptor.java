package com.fundoo.intercepter;

import com.fundoo.note.exception.AuthenticationException;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.service.RedisService;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@Component
public class NoteServiceInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String authorizationHeader = request.getHeader("AuthorizeToken");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            Object email = jwtUtil.verify(jwtToken);
            String token = redisService.getToken(email.toString());
            if (jwtToken.equals(token)) {
                request.setAttribute("email", email);
                return true;
            } else {
                throw new AuthenticationException("Authorization fail");
            }
        } else {
            throw new AuthenticationException("User Don't have permission");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(response);

//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//        byte[] contentAsByteArray = responseWrapper.getContentAsByteArray();
//        super.postHandle(request, responseWrapper, handler, modelAndView);
    }

}
