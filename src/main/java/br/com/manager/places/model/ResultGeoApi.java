package br.com.manager.places.model;

public class ResultGeoApi {
	public ResultGeoApi() {
		super();
	}

	private Geometry geometry;
	public ResultGeoApi(Geometry geometry, String place_id) {
		super();
		this.geometry = geometry;
		this.place_id = place_id;
	}

	private String place_id;

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

}
