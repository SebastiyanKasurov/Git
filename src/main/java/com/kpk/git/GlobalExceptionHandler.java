package com.kpk.git;

import com.kpk.git.util.ResponseMessage;
import com.kpk.git.util.exceptions.AbstractClientException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseMessage handleClientError(AbstractClientException e) {
		return new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseMessage handleServerError(Exception e) {
		return new ResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
	}
}
