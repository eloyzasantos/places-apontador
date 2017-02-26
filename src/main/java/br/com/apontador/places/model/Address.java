package br.com.apontador.places.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Address {
	
	@NotNull
	@Min(3)
	private String street;
	
	@NotNull
	@Min(1)
	private String streetNumber;
	
	@NotNull
	@Min(5)
	private String district;
	
	@NotNull
	@Min(2)
	private String city;
	
	@NotNull
	@Min(2)
	private String state;
	
	@NotNull
	@Min(2)
	private String country;
	
	@NotNull
	@Min(5)
	private String zipcode;
	
	private Location location;
	

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
