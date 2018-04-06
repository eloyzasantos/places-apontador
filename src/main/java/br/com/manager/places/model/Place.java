package br.com.manager.places.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Place {

	private String _id;
	private String name;
	private boolean active;
	
	private Address address;


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
}
