package br.com.manager.places.model;

import java.util.List;

public class ResponseGeoApi {
	
	private List<ResultGeoApi> results;
	public ResponseGeoApi(List<ResultGeoApi> results, String status) {
		super();
		this.results = results;
		this.status = status;
	}

	public ResponseGeoApi() {
		super();
	}

	private String status;

	public List<ResultGeoApi> getResults() {
		return results;
	}

	public void setResults(List<ResultGeoApi> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
