//package br.com.apontador.service;
//
//import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
//import static org.junit.Assert.*;
//
//import org.bson.types.ObjectId;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.github.fakemongo.junit.FongoRule;
//import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
//import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
//import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
//import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
//
//import br.com.apontador.places.config.AppConfig;
//import br.com.apontador.places.config.AppConfigTest;
//import br.com.apontador.places.exception.CoordinatesNotFound;
//import br.com.apontador.places.exception.DuplicatePlace;
//import br.com.apontador.places.exception.PlaceNotFound;
//import br.com.apontador.places.model.Address;
//import br.com.apontador.places.model.Place;
//import br.com.apontador.places.repository.PlaceRepository;
//import br.com.apontador.places.service.PlaceService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={AppConfig.class})
//public class PlaceServiceTest {
//
//	 @Autowired private ApplicationContext applicationContext;
//	 
//	 @Autowired private PlaceService placeService;
//	 
//	 private Place getPlace() {
//		 Place place = new Place();
//		 place.set_id("58b48602950d511364272097");
//		 place.setName("Curso de Costura");
//		 Address address = new Address("Rua Josefa Alves de Siqueira", "173", "Jardim Anhanguera",
//				 "Praia Grande", "SP", "BR", "11718000", null);
//		 place.setAddress(address);
//		 return place;
//	 }
//	 
//	 @Test
//	 @UsingDataSet(locations = {"/places.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
//	 public void mustCreateAPlace() throws CoordinatesNotFound, DuplicatePlace, PlaceNotFound{
//         Place place = this.placeService.get("58b48602950d511364272097");
//         
//         assertEquals(place.getName(), "Curso de Costura");
//	 }
//		
//	
//}
