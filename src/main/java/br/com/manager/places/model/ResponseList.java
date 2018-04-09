package br.com.manager.places.model;

import java.util.List;

import br.com.manager.places.domain.Place;

public class ResponseList extends Paginator {
	private List<Place> results;

	public List<Place> getResults() {
		return results;
	}

	public void setResults(List<Place> results) {
		this.results = results;
	}


}
