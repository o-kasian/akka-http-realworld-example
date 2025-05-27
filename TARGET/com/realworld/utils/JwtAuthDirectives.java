package com.realworld.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthDirectives extends OncePerRequestFilter {

    private final String secretKey;
    private final ObjectMapper objectMapper;

    public JwtAuthDirectives(String secretKey, ObjectMapper objectMapper) {
        this.secretKey = secretKey;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (authHeader != null && authHeader.startsWith("Token ")) {
            try {
                String token = authHeader.substring(6); // Remove "Token " prefix
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                DecodedJWT jwt = JWT.require(algorithm)
                        .build()
                        .verify(token);

                String payload = jwt.getPayload();
                AuthTokenContent authContent = objectMapper.readValue(
                    new String(java.util.Base64.getDecoder().decode(payload)),
                    AuthTokenContent.class
                );

                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        authContent.getUserId(),
                        null,
                        Collections.emptyList()
                    );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        
        filterChain.doFilter(request, response);
    }
}