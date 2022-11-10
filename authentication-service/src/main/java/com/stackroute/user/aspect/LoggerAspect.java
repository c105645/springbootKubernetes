package com.stackroute.user.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggerAspect {

	  private Logger logger = LoggerFactory.getLogger(LoggerAspect.class.getName());

	  @Around(value = "@annotation(ToLog)")
	  public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		 String methodName = joinPoint.getSignature().getName();
		 Object[] arguments = joinPoint.getArgs();
		    	    
	     logger.info("@Before Aspect");
		 logger.info("Method " + methodName +
		       " with parameters " + Arrays.asList(arguments) +
		       " will execute");		    
	    Object returnedValue = joinPoint.proceed();
	    logger.info("Logging Aspect: Method executed and returned " + returnedValue);
	    return returnedValue;
	  }
	  
	  
	  
	  @AfterReturning(value = "@annotation(ToLog)", returning = "returnedValue")
	  public void logAfterReturn(JoinPoint joinPoint, Object returnedValue) throws Throwable {
		 String methodName = joinPoint.getSignature().getName();
		 Object[] arguments = joinPoint.getArgs();
		    	    
	     logger.info("@AfterReturning Aspect");
		 logger.info("Method " + methodName +
		       " with parameters " + Arrays.asList(arguments) +
		       " will execute");		    
	    logger.info("Logging Aspect: Method executed and returned " + returnedValue);
	  }
	  
	  
	  
	  @AfterThrowing(value = "@annotation(ToLog)", throwing = "ex")
	  public void logAfterThrowing(JoinPoint joinPoint, Exception ex) throws Throwable {
		 String methodName = joinPoint.getSignature().getName();
		 Object[] arguments = joinPoint.getArgs();
		    	    
	     logger.info("@AfterReturning Aspect");
		 logger.info("Method " + methodName +
		       " with parameters " + Arrays.asList(arguments) +
		       " will execute");		    
	    logger.info("Logging Aspect: Method executed and throwed " + ex);
	  }
	  
	  

	  public void setLogger(Logger logger) {
	    this.logger = logger;
	  }


}
