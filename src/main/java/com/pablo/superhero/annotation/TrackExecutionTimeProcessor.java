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
		log.info(formatInfo(point, endTime, startTime));
		return object;
	}

	private String formatInfo(ProceedingJoinPoint point, long endTime, long startTime) {
		StringBuilder sb = new StringBuilder();
		sb.append("Class Name: ");
		sb.append(point.getSignature().getDeclaringTypeName());
		sb.append(". Method Name: ");
		sb.append(point.getSignature().getName());
		sb.append(". Time taken for Execution is : ");
		sb.append(endTime - startTime);
		sb.append("ms");
		return sb.toString();
	}
}
