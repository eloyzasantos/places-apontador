package br.com.manager.places.util;

import br.com.manager.places.model.Place;
import br.com.manager.places.model.ResponseGeoApi;

public class Validator {
	
	public static boolean validateResponseApiGeo(ResponseGeoApi response) {
		if (response != null && response.getStatus() != null && 
			response.getStatus().equals("OK") && response.getResults() != null &&
			response.getResults().get(0) != null && response.getResults().get(0).getGeometry() != null  &&
			response.getResults().get(0).getGeometry().getLocation() != null) {
			return true;
		}
		return false;
	}
	
	public static boolean validatePlace(Place place) {
		if (place != null && place.getName() != null && place.getAddress() != null &&
			!place.getAddress().getCity().isEmpty() && !place.getAddress().getCountry().isEmpty() &&
			!place.getAddress().getDistrict().isEmpty() && !place.getAddress().getState().isEmpty() &&
			!place.getAddress().getStreet().isEmpty() && !place.getAddress().getStreetNumber().isEmpty() &&
			place.getAddress().getZipcode() != null && !place.getName().isEmpty()) {
			return true;
		}
		return false;
	}

}
