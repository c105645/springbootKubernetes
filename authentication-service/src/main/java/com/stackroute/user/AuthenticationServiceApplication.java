package com.stackroute.user;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;



/*
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration 
 * and @ComponentScan with their default attributes
 */

@SpringBootApplication
@EnableAspectJAutoProxy
//@EnableEurekaClient
public class AuthenticationServiceApplication {

	/*
	 * You need to run SpringApplication.run, because this method start whole spring
	 * framework. Code below integrates your main() with SpringBoot
	 */

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}
	
	  /**
	   * Fetches a ModelMapper instance.
	   *
	   * @return ModelMapper
	   */
	  @Bean // Want a new obj every time
	  @Scope("prototype")
	  public ModelMapper modelMapper() {
		  ModelMapper modelMapper = new ModelMapper();
		  return modelMapper;
	  }
	  
  }


