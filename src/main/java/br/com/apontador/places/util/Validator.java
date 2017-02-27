package br.com.apontador.places.util;

import br.com.apontador.places.model.Place;
import br.com.apontador.places.model.ResponseGeoApi;

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
			place.getAddress().getCity() != null && place.getAddress().getCountry() != null &&
			place.getAddress().getDistrict() != null && place.getAddress().getState() != null &&
			place.getAddress().getStreet() != null && place.getAddress().getStreetNumber() != null &&
			place.getAddress().getZipcode() != null) {
			return true;
		}
		return false;
	}

}
