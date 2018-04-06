package br.com.manager.places.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Place not found")
public class PlaceNotFound extends Exception {
      
	public PlaceNotFound() {}

	public PlaceNotFound(String message)
	{
		super(message);
	}
}
