package br.com.apontador.places.model;

public class Location {
	
	public Location(String type, float[] coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}

	private String type;
	
	private float[] coordinates;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	}

}
