package br.com.apontador.places.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.apontador.places.exception.CoordinatesNotFound;
import br.com.apontador.places.exception.DuplicatePlace;
import br.com.apontador.places.exception.InvalidPlaceBody;
import br.com.apontador.places.exception.PlaceNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.Place;
import br.com.apontador.places.model.ResponseList;
import br.com.apontador.places.model.ResponseSearchNear;
import br.com.apontador.places.service.PlaceService;
import br.com.apontador.places.util.Validator;

@Controller
public class PlaceController {
	
	@Autowired PlaceService placeService;
	
	@RequestMapping(value = "/save",  method = RequestMethod.POST, consumes = "application/json", produces = "application/json" )
	@ResponseBody
	public ResponseEntity save(@RequestBody Place place) throws CoordinatesNotFound, DuplicatePlace, InvalidPlaceBody, PlaceNotFound {
		if (!Validator.validatePlace(place)) throw new InvalidPlaceBody();
		
		placeService.save(place);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@RequestMapping(value = "/update/{id}",  method = RequestMethod.PUT, consumes = "application/json", produces = "application/json" )
	@ResponseBody
	public ResponseEntity save(@PathVariable(value="id") String id, @RequestBody Place place) throws CoordinatesNotFound, DuplicatePlace, InvalidPlaceBody, PlaceNotFound {
		if (!Validator.validatePlace(place)) throw new InvalidPlaceBody();
		
		place.set_id(id);
		placeService.save(place);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@RequestMapping(value = "/list",  method = RequestMethod.GET)
	@ResponseBody
	public ResponseList search(@RequestParam(name="page", required=false, defaultValue="0") int page, 
			@RequestParam(name="rows", required=false, defaultValue="10") int rows) {
		return placeService.list(page > 0 ? page - 1 : page, rows);
	}
	
	@RequestMapping(value = "/search",  method = RequestMethod.GET)
	@ResponseBody
	public ResponseSearchNear search(@RequestParam(name="page", required=false, defaultValue="0") int page, 
			@RequestParam(name="rows", required=false, defaultValue="10") int rows, 
			@RequestParam(name="q", required=true) String q,
			@RequestParam(name="maxDistance", required=false, defaultValue = "2.0") Double maxDistance,
			Address address) throws CoordinatesNotFound {
		return placeService.search(page > 0 ? page - 1 : page, rows, q, address, maxDistance);
	}
	
	@RequestMapping(value = "/get/{id}",  method = RequestMethod.GET)
	@ResponseBody
	public Place get(@PathVariable(value="id") String id) throws PlaceNotFound {
		return placeService.get(id);
	}
	
	@RequestMapping(value = "/disable/{id}",  method = RequestMethod.GET)
	@ResponseBody
	public void disable(@PathVariable(value="id") String id) throws PlaceNotFound {
		placeService.changeStatus(id, false);
	}
	
	@RequestMapping(value = "/activate/{id}",  method = RequestMethod.GET)
	@ResponseBody
	public void activate(@PathVariable(value="id") String id) throws PlaceNotFound {
		placeService.changeStatus(id, true);
	}
	
}
