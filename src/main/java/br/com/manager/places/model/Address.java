package br.com.manager.places.model;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class Address {
	
	public Address(String street, String streetNumber, String district, String city, String state, String country,
			String zipcode, GeoJsonPoint location) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.district = district;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.location = location;
	}
	
	public Address() {
		super();
	}

	private String street;
	
	private String streetNumber;
	
	private String district;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String zipcode;
	
	private String placeId;
	
	private GeoJsonPoint location;
	

	public GeoJsonPoint getLocation() {
		return location;
	}
	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}
	public String getStreet() {
		return street != null ? street : "";
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetNumber() {
		return streetNumber != null ? streetNumber : "";
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getDistrict() {
		return district != null ? district : "";
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city != null ? city : "";
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state != null ? state : "";
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country != null ? country : "";
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode != null ? zipcode : "";
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode != null ? zipcode.replaceAll("-", "").replaceAll(".", "") : null;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
}
