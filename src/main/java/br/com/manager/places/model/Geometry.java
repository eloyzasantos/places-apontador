package br.com.manager.places.model;

public class Geometry {
	private Location location;

	public Location getLocation() {
		return location;
	}

	public Geometry(Location location) {
		super();
		this.location = location;
	}

	public Geometry() {
		super();
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}