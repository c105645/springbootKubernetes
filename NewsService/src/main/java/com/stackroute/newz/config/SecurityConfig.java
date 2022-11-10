package com.stackroute.newz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.stackroute.newz.jwtfilter.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    	this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Value("${jwt.signing.key}")
    private String signingKey;
        @Bean
	    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

              	    	 
	        http.cors().configurationSource(corsConfiguration())
	                .and()
	                .csrf().disable().httpBasic()
	                .and()
	                .authorizeRequests()
	                .antMatchers("/swagger-ui*/**", "/v3/api-docs/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        
        	
       	 http.addFilterAfter(
                    jwtAuthenticationFilter,
                    BasicAuthenticationFilter.class
                );

	        return http.build();

	    }
       
    	
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        
      
        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration authConfig) throws Exception {
            return authConfig.getAuthenticationManager();
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
