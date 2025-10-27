package com.freq.arvand.arvand.security;

import com.freq.arvand.arvand.userSession.UserSessionImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private JwtSevice jwtSevice;

    public JwtFilter(JwtSevice jwtSevice) {
        this.jwtSevice = jwtSevice;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = authHeader.substring(7);
            String username = jwtSevice.extractUserName(jwt);
            Authentication authentication = UserSessionImpl.getAuthentication(username);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            if(jwtSevice.isTokenExpired(jwt)){
                Authentication _authentication = jwtSevice.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(_authentication);
                UserSessionImpl.addAuthentication(username,_authentication);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);

    }
}
