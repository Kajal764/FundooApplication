//package com.fundoo.filter;
//
//import com.fundoo.note.exception.AuthenticationException;
//import com.fundoo.user.repository.UserRepository;
//import com.fundoo.user.service.RedisService;
//import com.fundoo.user.utility.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = {"/fundoo/note/*", "/fundoo/label/*"})
//@CrossOrigin
//public class AuthenticationFilter implements Filter {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//    @Autowired
//    HandlerExceptionResolver handlerExceptionResolver;
//
//    @Autowired
//    RedisService redisService;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String authorizationHeader = request.getHeader("AuthorizeToken");
//        System.out.println("auth   " + authorizationHeader);
//        try {
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                String jwtToken = authorizationHeader.substring(7);
//                Object email = jwtUtil.verify(jwtToken);
//                String token = redisService.getToken(email.toString());
//                if (jwtToken.equals(token)) {
//                    servletRequest.setAttribute("email", email);
//                    filterChain.doFilter(request, response);
//                } else {
//                    throw new AuthenticationException("Authorization fail");
//                }
//            } else {
//                throw new AuthenticationException("User Don't have permission");
//            }
//        } catch (AuthenticationException exception) {
//            handlerExceptionResolver.resolveException(request, response, null, exception);
//        }
//    }
//
//}
