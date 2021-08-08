package com.pg.iloveblog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.pg.iloveblog.dto.ResponseDTO;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	@ExceptionHandler(value=Exception.class) //value에 해당하는 예외가 발생하면 handler 호출
	public ResponseDTO<String> handler(Exception e){ //발생한 예외가 파라미터로 전달된다.
		System.out.println("Exception 발생!!");
		return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
