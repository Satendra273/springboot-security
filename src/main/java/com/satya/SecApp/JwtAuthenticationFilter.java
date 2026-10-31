package com.satya.SecApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satya.SecApp.service.UserServiceImp;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserServiceImp userServiceImp;
    private final ObjectMapper objectMapper = new ObjectMapper();



    public JwtAuthenticationFilter(JwtTokenService jwtTokenService, UserServiceImp userServiceImp) {
        this.jwtTokenService = jwtTokenService;
        this.userServiceImp = userServiceImp;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("filter is call ");

        String authHeader = request.getHeader("Authorization");

       //no auth header
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        //Auth header is exist but format is wrong
        if(!authHeader.startsWith("Bearer ")){
            sendUnauthorizedResponse(response , "Invalid Authorization header. Expected Bearer token");
         return;
        }

        String trimToken = authHeader.substring(7).trim();

        // Empty token
        if(trimToken.isEmpty()){
            sendUnauthorizedResponse(response, "JWT token is missing.");
            return;
        }

        try {

            String username = jwtTokenService.getUsername(trimToken);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userServiceImp.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
            filterChain.doFilter(request, response);

        }catch (UsernameNotFoundException e ){
            SecurityContextHolder.clearContext();
            sendUnauthorizedResponse(response,"User associated with this token was not found." );

        }catch (JwtException | IllegalArgumentException e) {

            SecurityContextHolder.clearContext();

            sendUnauthorizedResponse(
                    response,
                    "Invalid or expired JWT token."
            );
        }





    }









    private void sendUnauthorizedResponse(
            HttpServletResponse response,
            String message)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorResponse = Map.of(
                "status", 401,
                "error", "UNAUTHORIZED",
                "message", message
        );

        objectMapper.writeValue(
                response.getWriter(),
                errorResponse
        );
    }


}


