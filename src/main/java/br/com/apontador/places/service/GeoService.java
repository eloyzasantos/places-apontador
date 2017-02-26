package br.com.apontador.places.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.apontador.places.exception.CoordinatesNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.Location;
import br.com.apontador.places.model.Place;
import br.com.apontador.places.model.ResponseGeoApi;

@Service
public class GeoService {

	@Value("${maps.google.api.url}")
	private String urlApi;
	
	public void findGeoLocation(Address address) throws CoordinatesNotFound {
		
		createUrl(address);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseGeoApi result = restTemplate.getForObject(urlApi, ResponseGeoApi.class);
		
		if (result == null || result.getResults() == null || result.getResults().get(0) == null || 
				result.getResults().get(0).getGeometry() == null || result.getResults().get(0).getGeometry().getLocation() == null) {
			throw new CoordinatesNotFound("Invalid Address. Coordinates not found.");
		}
		
		float lg = result.getResults().get(0).getGeometry().getLocation().getLng();
		float lt = result.getResults().get(0).getGeometry().getLocation().getLat();
		
		address.setLocation(new Location("Point", new float[] {lg, lt}));
	}
	
	private void createUrl(Address address) {
		StringBuilder formattedAddress = new StringBuilder(address.getStreetNumber())
				.append(" ").append(address.getStreet()).append(" ")
				.append(address.getDistrict()).append(" ")
				.append(address.getCity()).append(" ").append(address.getState());
		
		urlApi = String.format(urlApi, formattedAddress.toString(), address.getCountry(), address.getZipcode());
	}
}
