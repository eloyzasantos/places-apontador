package br.com.apontador.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.fakemongo.junit.FongoRule;

import br.com.apontador.places.config.AppConfigTest;
import br.com.apontador.places.exception.CoordinatesNotFound;
import br.com.apontador.places.exception.DuplicatePlace;
import br.com.apontador.places.exception.PlaceNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.Place;
import br.com.apontador.places.model.ResponseList;
import br.com.apontador.places.service.PlaceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfigTest.class})
public class PlaceServiceTest {

	 @Rule
	 public FongoRule fongoRule = new FongoRule();
	
	 @Autowired private PlaceService placeService;

	 @Before
	 public void before() throws CoordinatesNotFound, DuplicatePlace, PlaceNotFound {
		 Place place = getPlace();
		 placeService.save(place);
	 }
	 
	 @Test(expected=PlaceNotFound.class)
	 public void mustThrowPlaceNotFoundWhenPlaceNotExists() throws PlaceNotFound {
         this.placeService.get("123");
	 }
	 
	 @Test(expected=DuplicatePlace.class)
	 public void mustThrowDuplicatePlaceExceptionWhenPlaceAlreadyExists() throws PlaceNotFound, CoordinatesNotFound, DuplicatePlace{
		 Place place = getPlace();
		 place.set_id(null);
		 placeService.save(place);
	 }
	 
	 @Test(expected=CoordinatesNotFound.class)
	 public void mustThrowCoordinatesNotFoundExceptionWhenWhenLocationNotFound() throws PlaceNotFound, CoordinatesNotFound, DuplicatePlace{
		 Place place = getPlace();
		 place.setAddress(new Address("yrdyr", "yrdyr", "yrdyr", "yrdyr", "yrdyr", "yrdyr", "yrdyr", null));
		 
		 placeService.save(place);
	 }
	 
	 @Test
	 public void mustSaveAndListPlaces() throws PlaceNotFound, CoordinatesNotFound, DuplicatePlace{
		 Place place = getPlace();
		 
         ResponseList response = this.placeService.list(0, 10);
         
         assertEquals(response.getResults().size(), 1);
         assertEquals(response.getFound(), 1);
         assertEquals(response.getPage(), 1);
         assertEquals(response.getPages(), 1);
         
         Place placeResult =  response.getResults().get(0);
         
         assertEquals(placeResult.getName(), place.getName());
         assertEquals(placeResult.getAddress().getDistrict(), place.getAddress().getDistrict());
         assertEquals(placeResult.getAddress().getStreet(), place.getAddress().getStreet());
         assertEquals(placeResult.getAddress().getStreetNumber(), place.getAddress().getStreetNumber());
         assertEquals(placeResult.getAddress().getCity(), place.getAddress().getCity());
         assertEquals(placeResult.getAddress().getCountry(), place.getAddress().getCountry());
         assertEquals(placeResult.getAddress().getZipcode(), place.getAddress().getZipcode());
         assertEquals(placeResult.isActive(), true);
         assertNotNull(placeResult.getAddress().getLocation());
         assertNotNull(placeResult.getAddress().getLocation().getCoordinates());
         assertEquals(placeResult.getAddress().getLocation().getCoordinates().size(), 2);
	 }
	 
	 
	 private static Place getPlace() {
		 Place place = new Place();
		 place.setName("Curso de Costura");
		 Address address = new Address("Rua Josefa Alves de Siqueira", "173", "Jardim Anhanguera",
				 "Praia Grande", "SP", "BR", "11718000", null);
		 place.setAddress(address);
		 return place;
	 }
	 
		
	
}
