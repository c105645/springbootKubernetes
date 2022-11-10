package com.stackroute.userprofile;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
/*
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration 
 * and @ComponentScan with their default attributes
 */
import org.springframework.context.annotation.Scope;



@SpringBootApplication
//@EnableEurekaClient
public class UserProfileServiceApplication {

	/*
	 * Define the bean for Filter registration. Create a new FilterRegistrationBean
	 * object and use setFilter() method to set new instance of JwtFilter object.
	 * Also specifies the Url patterns for registration bean.
	 */

   
	/*
	 * 
	 * You need to run SpringApplication.run, because this method start whole spring
	 * framework. Code below integrates your main() with SpringBoot.
	 */

	public static void main(String[] args) {
		SpringApplication.run(UserProfileServiceApplication.class, args);
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
