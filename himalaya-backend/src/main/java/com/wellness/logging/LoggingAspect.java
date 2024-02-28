package com.wellness.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Aspect
@Component
public class LoggingAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@AfterThrowing(pointcut = "execution(* com.wellness..*(..))", throwing = "exception")
	public void logException(JoinPoint joinPoint, Throwable exception) {
		logger.error("Exception in {}.{}() with cause = '{}'", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), exception.getMessage());
	}
	
	@Before("execution(* com.wellness..*(..))")
	public void logStart(JoinPoint joinPoint) {
		logger.debug("Method {} is about to be executed." , joinPoint.getSignature().getName());;
	}
	
	@After("execution(* com.wellness..*(..))")
	public void logEnd(JoinPoint joinPoint) {
		logger.debug("Method {} has been successfully executed." , joinPoint.getSignature().getName());;
	}
	
//	@AfterReturning(pointcut = "execution(* com.wellness..*(..))")
//	public void logResult(JoinPoint joinPoint ) {
//		logger.info("Method {} has given" , joinPoint.getSignature().getName());;
//	}
}
