package br.com.manager.places.model;

import org.springframework.data.geo.Distance;

public class ResultSearchNear {

	private Place place;
	private Distance distance;
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public Distance getDistance() {
		return distance;
	}
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	
}
