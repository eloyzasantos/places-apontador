package br.com.apontador.places.model;

import java.util.List;

public class ResponseSearchNear extends Paginator {
	
	private List<ResultSearchNear> results;

	public List<ResultSearchNear> getResults() {
		return results;
	}

	public void setResults(List<ResultSearchNear> results) {
		this.results = results;
	}


}
