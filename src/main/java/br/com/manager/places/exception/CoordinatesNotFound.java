package br.com.manager.places.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Address is Invalid. Coordinates not found.")  // 404
public class CoordinatesNotFound extends Exception {
      
	public CoordinatesNotFound() {}

	public CoordinatesNotFound(String message)
	{
		super(message);
	}
}
