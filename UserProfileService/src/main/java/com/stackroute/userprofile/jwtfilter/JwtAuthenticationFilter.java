package com.stackroute.userprofile.jwtfilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stackroute.userprofile.util.exception.NewsUnauthorizedException;
import com.stackroute.userprofile.util.jwt.JwtTokenUtil;

import io.jsonwebtoken.Claims;

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
        	response.setContentType("application/text");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
  }

}
    

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
       return request.getServletPath().indexOf("/swagger-") != -1 || request.getServletPath().indexOf("/v3/api-docs") !=-1;
    }
}



