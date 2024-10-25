package com.group.practicetutorial.filter;

import com.group.practicetutorial.service.CustomUserDetailsImp;
import com.group.practicetutorial.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZWFzdCIsImlhdCI6MTcyOTc3NTE5NSwiZXhwIjoxNzI5Nzc1NDk1fQ.TAjvzR_pfpY3Bozl8aYSc2AIVHL5cvlxiLmjjUeDf-Q

        String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtUtil.extractUserName(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = context.getBean(CustomUserDetailsImp.class).loadUserByUsername(username);

            if(jwtUtil.validateToken(token, userDetails)){

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  userDetails.getUsername(),
                  userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
