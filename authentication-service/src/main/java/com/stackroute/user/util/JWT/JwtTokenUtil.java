package com.stackroute.user.util.JWT;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
	
	 @Value("${jwt.signing.key}")
	    private String signingKey;
	
	 public String generateToken(Authentication a) {
		 SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
		 long currentMillis=System.currentTimeMillis();
	       long expiryMillis=currentMillis+(60*60*1000*24);
	        Date expiryDate=new Date(expiryMillis);
	        Date createdAt = new Date(currentMillis);
	        
	        String scope = a.getAuthorities().stream()
                 .map(GrantedAuthority::getAuthority)
                 .collect(Collectors.joining(" "));
  
         
         String jwt = Jwts.builder()
                 .setClaims(Map.of("username", a.getName(), "issuedAt", createdAt, "expiresAt",expiryDate,
                 		"subject", a.getName(), "scope", scope))
                 .signWith(key)
                 .compact();
         
         return jwt;
	    }
	 
     public Claims decodeToken(String token) {
         SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
            Jws<Claims>claimsHolder =Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
                  return claimsHolder.getBody();
        }

}
