package com.kinsight.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class BaseControllerAdvice {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = { BaseException.class } )
  	@ResponseStatus(HttpStatus.OK)
  	@ResponseBody
  	private BaseResponse<?> handleBaseException(BaseException e, WebRequest request ,HttpServletResponse  response) {
	response.setCharacterEncoding("UTF-8");
	
	
	
  	return new BaseResponse<String>(e.getResponseCode(), messageSource.getMessage(e.getResponseCode().name(),e.getArgs(),null));
  	}
}
