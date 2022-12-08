package com.pablo.superhero.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TrackExecutionTimeProcessor {

	@Around("@annotation(com.pablo.superhero.annotation.TrackExecutionTime)")
	public Object executionTime(ProceedingJoinPoint point) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object object = point.proceed();
		long endTime = System.currentTimeMillis();
		log.info("Class Name: " + point.getSignature().getDeclaringTypeName() + ". Method Name: "
				+ point.getSignature().getName() + ". Time taken for Execution is : " + (endTime - startTime) + "ms");
		return object;
	}
}
