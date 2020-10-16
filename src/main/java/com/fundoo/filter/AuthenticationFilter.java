package com.fundoo.filter;

import com.fundoo.note.exception.AuthenticationException;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.service.RedisService;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@WebFilter(urlPatterns = {"/fundoo/note/*", "/fundoo/label/*"})
@CrossOrigin
public class AuthenticationFilter implements Filter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    RedisService redisService;

//    private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        // Access-Control-Allow-Origin
//        String origin = request.getHeader("Origin");
//        response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
//        response.setHeader("Vary", "Origin");
//
//        // Access-Control-Max-Age
//        response.setHeader("Access-Control-Max-Age", "3600");
//
//        // Access-Control-Allow-Credentials
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        // Access-Control-Allow-Methods
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//
//        // Access-Control-Allow-Headers
//        response.setHeader("Access-Control-Allow-Headers",
//                "Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");

        String authorizationHeader = request.getHeader("AuthorizeToken");
        System.out.println("auth   " + authorizationHeader);
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwtToken = authorizationHeader.substring(7);
                Object email = jwtUtil.verify(jwtToken);
                String token = redisService.getToken(email.toString());
                if (jwtToken.equals(token)) {
                    servletRequest.setAttribute("email", email);
                    filterChain.doFilter(request, response);
                } else {
                    throw new AuthenticationException("Authorization fail");
                }
            } else {
                throw new AuthenticationException("User Don't have permission");
            }
        } catch (AuthenticationException exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/fundoo/note/**", config);
        return new CorsFilter(source);
    }

}
