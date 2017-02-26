package br.com.apontador.places.model;

import java.util.List;

public class ResponseGeoApi {
	
	private List<Result> results;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
}
