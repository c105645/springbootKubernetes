package com.stackroute.newz;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


/*
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration 
 * and @ComponentScan with their default attributes
 */

@SpringBootApplication
//@EnableEurekaClient
public class NewsSourceServiceApplication {
    
	/*
	 * You need to run SpringApplication.run, because this method start whole spring
	 * framework. Code below integrates your main() with SpringBoot
	 */

	public static void main(String[] args) {
		SpringApplication.run(NewsSourceServiceApplication.class, args);
	}

	
		  @Bean // Want a new obj every time
		  @Scope("prototype")
		  public ModelMapper modelMapper() {
			  ModelMapper modelMapper = new ModelMapper();
//			  modelMapper.getConfiguration().setSkipNullEnabled(true);
			  return modelMapper;

		  }
}
