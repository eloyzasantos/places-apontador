package br.com.manager.places.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.manager.places.model.Address;

@Document
public class Place {

	private String _id;
	private String name;
	private boolean active;
	
	private Address address;
	
	public Place() {
		super();
	}

	public Place(String name, Address address) {
		super();
		this.name = name;
		this.address = address;
	}
	
	public Place(String id, String name, Address address) {
		super();
		this._id = id;
		this.name = name;
		this.address = address;
	}


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
