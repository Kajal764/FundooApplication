package com.fundoo.filter;

import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.service.RedisService;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = "/fundoo/note/*")
public class AuthenticationFilter implements Filter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil=new JwtUtil();

    @Autowired
    RedisService redisService =new RedisService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            Object email = jwtUtil.verify(jwtToken);
            String token = redisService.getToken(email.toString());
            if (jwtToken.equals(token)) {
                servletRequest.setAttribute("email", email);
                filterChain.doFilter(request, response);
            } else {
                PrintWriter out = response.getWriter();
                out.println("User Don't have permission");
                //throw new AuthenticationException("User Don't have permission");
            }
        }
    }



}
