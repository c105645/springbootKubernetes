package com.stackroute.user.jwtFilters;

import org.springframework.cloud.gateway.filter.GatewayFilter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.stackroute.user.util.JWT.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {


@Value("${jwt.signing.key}")
   private String signingKey;
   
   private JwtTokenUtil jwtutil;

   
   public JwtAuthenticationFilter(JwtTokenUtil jwtutil) {
       super(Config.class); 
       this.jwtutil = jwtutil;
   }
   



@Override
public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {        
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization Info");
        }
        
        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        String[] parts = authHeader.split(" ");

        if (parts.length != 2 || !"Bearer".equals(parts[0])) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect Authorization Token Structure");
        }
        
            try {
                Claims claims = jwtutil.decodeToken(parts[1]);
                String username = String.valueOf(claims.get("username"));
                      
                  exchange.getRequest()
                      .mutate()
                      .header("X-auth-user-id", String.valueOf(username))
                      .header("X-auth-user-scope", String.valueOf(claims.get("scope")));
                  Set<GrantedAuthority> authorities = Arrays.asList(String.valueOf(claims.get("scope"))
                          .split(" "))
                          .stream()
                          .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                          .collect(Collectors.toSet());
                  
                  var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                  SecurityContextHolder.getContext().setAuthentication(auth);                  
            }
            catch (Exception ex) {

                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("WWW_AUTH_HEADER", ex.getMessage());

                return response.setComplete();
            }

        return chain.filter(exchange);
    });
}


public static class Config {

}

}


