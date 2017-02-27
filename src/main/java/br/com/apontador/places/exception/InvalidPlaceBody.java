package br.com.apontador.places.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Name and Address fields are required.")
public class InvalidPlaceBody extends Exception  {
    
	public InvalidPlaceBody() {}

	public InvalidPlaceBody(String message)
	{
		super(message);
	}
}
