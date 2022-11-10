package com.stackroute.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;



@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
	

    @Value("${jwt.signing.key}")
    private String signingKey;
    

    
        @Bean
	    protected SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
              	    	 
	        http.cors().configurationSource(corsConfiguration())
	                .and()
	                .csrf().disable().httpBasic()
	                .and()
	                .authorizeExchange()
	                .pathMatchers("/**").permitAll();
//	                 .pathMatchers("api/v1/auth/register").permitAll()
//	                .pathMatchers("/api/v1/auth/login").permitAll()
//                    .pathMatchers("/swagger-ui/**").permitAll()
//                    .pathMatchers("/actuator/**").permitAll()
//	                .anyExchange().authenticated();
	        
	        return http.build();

	    }
       
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        
           
	    @Bean
	    public CorsConfigurationSource corsConfiguration() {
	        UrlBasedCorsConfigurationSource configuration = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.addAllowedOrigin("http://ourfrontend.com");
	        corsConfiguration.addAllowedMethod("*");
	        corsConfiguration.addAllowedHeader("*");
	        configuration.registerCorsConfiguration("/**", corsConfiguration);
	        return configuration;
	    }
	    

	    


}
