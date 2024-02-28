package com.wellness.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WellnessExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> handleUserException(UserException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<String> handleProductException(ProductException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	@ExceptionHandler(CartException.class)
	public ResponseEntity<String> handleCartException(CartException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(AddressException.class)
	public ResponseEntity<String> handleAddressException(AddressException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<String> handleOrderException(OrderException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
}
