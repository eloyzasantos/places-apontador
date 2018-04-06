package br.com.apontador.places.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Duplicate place: Found same address with similar place name")  // 404
public class DuplicatePlace extends Exception {
	
	public DuplicatePlace() {}

	public DuplicatePlace(String message)
	{
		super(message);
	}
}