package br.com.apontador.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import br.com.apontador.places.exception.CoordinatesNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.Geometry;
import br.com.apontador.places.model.Location;
import br.com.apontador.places.model.ResponseGeoApi;
import br.com.apontador.places.model.ResultGeoApi;
import br.com.apontador.places.service.GeoService;

@RunWith(MockitoJUnitRunner.class)
public class GeoServiceTest extends GeoService {

	
	@Before
	public void before() {
		this.restTemplate = Mockito.mock(RestTemplate.class);
		this.urlApi = "maps.googleapis.com/json?address=%s&components=country:%s&components=postal_code:%s&key=AAA";
	}
	
	@Test
	public void mustFormatAddressCorrectly() {
		String formatted = formatAddress(getAddress());
		
		assertEquals("173 Rua Josefa Alves de Siqueira Jardim Anhanguera Praia Grande SP", formatted);
	}
	
	@Test
	public void mustCreateUrlCorrectly() {
		String url = createUrl(getAddress());
		
		assertEquals("maps.googleapis.com/json?address=173 Rua Josefa Alves de Siqueira Jardim Anhanguera Praia Grande SP"
				+ "&components=country:BR&components=postal_code:11718000&key=AAA"
				, url);
	}
	
	@Test
	public void mustSetGeoLocation() throws CoordinatesNotFound {
		when(restTemplate.getForObject(createUrl(getAddress()), ResponseGeoApi.class)).thenReturn(getResponse());
		
		Address address = getAddress();
		this.findGeoLocation(address);
		
		assertEquals(456789.55, address.getLocation().getX(), 0);
		assertEquals(45678.44, address.getLocation().getY(), 0);
		
	}
	
	private ResponseGeoApi getResponse() {
		Location location = new Location(45678.44, 456789.55);
		Geometry geo = new Geometry(location);
		ResultGeoApi result = new ResultGeoApi(geo, "asdasdasdas");
		List<ResultGeoApi> results = new ArrayList<ResultGeoApi>();
		results.add(result);
		return new ResponseGeoApi(results, "OK");
	}
	
	private Address getAddress() {
		return new Address("Rua Josefa Alves de Siqueira", "173", "Jardim Anhanguera",
				 "Praia Grande", "SP", "BR", "11718000", null);
	}
}
