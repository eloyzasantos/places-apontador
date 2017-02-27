package br.com.apontador.places.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.apontador.places.exception.CoordinatesNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.ResponseGeoApi;
import br.com.apontador.places.util.Validator;

@Service
public class GeoService {

	@Value("${maps.google.api.url}")
	private String urlApi;
	
	public void findGeoLocation(Address address) throws CoordinatesNotFound {
		
		address.setZipcode(address.getZipcode().replaceAll("-", "").replaceAll(".", ""));
		
		String url = createUrl(address);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseGeoApi result = restTemplate.getForObject(url, ResponseGeoApi.class);
		
		if (!Validator.validateResponseApiGeo(result)) {
			throw new CoordinatesNotFound("Invalid Address. Coordinates not found.");
		}
		
		double lg = result.getResults().get(0).getGeometry().getLocation().getLng();
		double lt = result.getResults().get(0).getGeometry().getLocation().getLat();
		
		address.setLocation(new GeoJsonPoint(lg, lt));
		address.setPlaceId(result.getResults().get(0).getPlace_id());
	}
	
	private String createUrl(Address address) {
		return String.format(urlApi, formatAddress(address), address.getCountry(), address.getZipcode());
	}
	
	private String formatAddress(Address address) {
		StringBuilder formattedAddress = new StringBuilder(address.getStreetNumber())
				.append(" ").append(address.getStreet()).append(" ")
				.append(address.getDistrict()).append(" ")
				.append(address.getCity()).append(" ").append(address.getState());
		
		return formattedAddress.toString();
	}
}
