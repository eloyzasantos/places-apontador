package br.com.apontador.places.exception;

public class PlaceNotFound extends Exception {
      
	public PlaceNotFound() {}

	public PlaceNotFound(String message)
	{
		super(message);
	}
}
