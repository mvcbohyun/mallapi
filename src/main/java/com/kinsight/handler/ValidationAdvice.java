package com.kinsight.handler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;

@Component
@Aspect
public class ValidationAdvice {

	@Around("execution(* com.kinsight.controller.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("rest controller 전에 타냐 ???????===================================");
		
		Object[] args = proceedingJoinPoint.getArgs();
		
		for(Object arg : args) {
			if(arg instanceof BindingResult) {
				System.out.println("유효성 검사중 입니다 ........");
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {
					for(FieldError error : bindingResult.getFieldErrors()) {
						throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {error.getField(),error.getDefaultMessage()});
					}
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
		
	}
}
