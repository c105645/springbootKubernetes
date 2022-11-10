package com.stackroute.newz.jwtfilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.authentication.BadCredentialsException;

import com.stackroute.newz.util.exception.NewsUnauthorizedException;
import com.stackroute.newz.util.jwt.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;

import org.springframework.security.authentication.BadCredentialsException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.signing.key}")
    private String signingKey;
    
    private JwtTokenUtil jwtutil;
    
    public JwtAuthenticationFilter(JwtTokenUtil jwtutil) {
    	this.jwtutil = jwtutil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException  {
        String jwt = request.getHeader("Authorization");
        try {
            if (jwt == null || jwt.isEmpty()|| !jwt.startsWith("Bearer")) {

            	throw new NewsUnauthorizedException("No Token provided in the request headers");
            }
            jwt = jwt.substring(7);
            Claims claims = jwtutil.decodeToken(jwt);
            String username = String.valueOf(claims.get("username"));
            Set<GrantedAuthority> authorities = Arrays.asList(String.valueOf(claims.get("scope"))
            		.split(" "))
            		.stream()
            		.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            		.collect(Collectors.toSet());

            var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
        	response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            return;
  }

}
    

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
       return request.getServletPath().indexOf("/swagger-") != -1 || request.getServletPath().indexOf("/v3/api-docs") !=-1;
    }
}



