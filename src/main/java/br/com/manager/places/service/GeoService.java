package br.com.manager.places.service;

import br.com.manager.places.exception.CoordinatesNotFound;
import br.com.manager.places.model.Address;
import br.com.manager.places.model.ResponseGeoApi;
import br.com.manager.places.model.ResultGeoApi;
import br.com.manager.places.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoService {

	@Value("${maps.google.api.url}")
	protected String urlApi;
	
	protected RestTemplate restTemplate = new RestTemplate();
	
	public void findGeoLocation(Address address) throws CoordinatesNotFound {
		
		String url = createUrl(address);
		
		ResponseGeoApi response = restTemplate.getForObject(url, ResponseGeoApi.class);
		
		if (!Validator.validateResponseApiGeo(response)) {
			throw new CoordinatesNotFound("Invalid Address. Coordinates not found.");
		}
		
		ResultGeoApi result = response.getResults().iterator().next();
		
		double lg = result.getGeometry().getLocation().getLng();
		double lt = result.getGeometry().getLocation().getLat();
		
		address.setLocation(new GeoJsonPoint(lg, lt));
		address.setPlaceId(result.getPlace_id());
	}
	
	protected String createUrl(Address address) {
		return String.format(urlApi, formatAddress(address), address.getCountry(), address.getZipcode());
	}
	
	protected String formatAddress(Address address) {
		return String.format("%s %s %s %s %s", 
				address.getStreetNumber(), address.getStreet(),
				address.getDistrict(), address.getCity(),
				address.getState());
//		StringBuilder formattedAddress = new StringBuilder(address.getStreetNumber())
//				.append(" ").append(address.getStreet()).append(" ")
//				.append(address.getDistrict()).append(" ")
//				.append(address.getCity()).append(" ").append(address.getState());
//		
//		return formattedAddress.toString();
	}
}
