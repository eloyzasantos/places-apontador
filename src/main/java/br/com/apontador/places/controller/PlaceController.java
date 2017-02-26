package br.com.apontador.places.controller;

import java.util.List;
import java.util.Map;

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
import br.com.apontador.places.exception.PlaceNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.Place;
import br.com.apontador.places.service.PlaceService;

@Controller
public class PlaceController {
	
	@Autowired PlaceService placeService;

	@RequestMapping(value = "/save",  method = RequestMethod.POST, consumes = "application/json", produces = "application/json" )
	@ResponseBody
	public ResponseEntity save(@RequestBody Place place) {
		try {
			placeService.save(place);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@RequestMapping(value = "/list",  method = RequestMethod.GET)
	@ResponseBody
	public List<Place> search(@RequestParam(name="start", required=true) int start, 
			@RequestParam(name="rows", required=true) int rows) {
		return placeService.search(start, rows);
	}
	
	@RequestMapping(value = "/search",  method = RequestMethod.GET)
	@ResponseBody
	public List<Map> search(@RequestParam(name="start", required=true) int start, 
			@RequestParam(name="rows", required=true) int rows, 
			@RequestParam(name="q", required=true) String q,
			@RequestParam(name="address") Address address,
			@RequestParam(name="maxDistance", required=false) double maxDistance) throws CoordinatesNotFound {
		return placeService.search(start, rows, q, address, maxDistance);
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
