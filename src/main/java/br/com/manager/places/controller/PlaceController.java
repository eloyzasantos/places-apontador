package br.com.manager.places.controller;

import br.com.manager.places.model.Address;
import br.com.manager.places.model.PlaceInput;
import br.com.manager.places.model.ResponseList;
import br.com.manager.places.model.ResponseSearchNear;
import br.com.manager.places.service.PlaceService;
import br.com.manager.places.util.Validator;
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

import br.com.manager.places.domain.Place;
import br.com.manager.places.exception.CoordinatesNotFound;
import br.com.manager.places.exception.DuplicatePlace;
import br.com.manager.places.exception.InvalidPlaceBody;
import br.com.manager.places.exception.PlaceNotFound;

@Controller
@RequestMapping("places")
public class PlaceController {
	
	@Autowired
    PlaceService placeService;
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json" )
	@ResponseBody
	public ResponseEntity save(@RequestBody PlaceInput place) throws CoordinatesNotFound, DuplicatePlace, InvalidPlaceBody, PlaceNotFound {
		if (!Validator.validatePlace(place)) throw new InvalidPlaceBody();
		
		placeService.save(new Place(place.getName(), place.getAddress()));
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	
	@RequestMapping(value = "/{id}",  method = RequestMethod.PUT, consumes = "application/json", produces = "application/json" )
	@ResponseBody
	public ResponseEntity save(@PathVariable(value="id") String id, @RequestBody PlaceInput place) throws CoordinatesNotFound, DuplicatePlace, InvalidPlaceBody, PlaceNotFound {
		if (!Validator.validatePlace(place)) throw new InvalidPlaceBody();
		
		placeService.save(new Place(id, place.getName(), place.getAddress()));
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@RequestMapping(method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/{id}",  method = RequestMethod.GET)
	@ResponseBody
	public Place get(@PathVariable(value="id") String id) throws PlaceNotFound {
		return placeService.get(id);
	}
	
	@RequestMapping(value = "/{id}",  method = RequestMethod.DELETE)
	@ResponseBody
	public void disable(@PathVariable(value="id") String id) throws PlaceNotFound {
		placeService.changeStatus(id, false);
	}
	
	@RequestMapping(value = "/{id}",  method = RequestMethod.PATCH)
	@ResponseBody
	public void activate(@PathVariable(value="id") String id) throws PlaceNotFound {
		placeService.changeStatus(id, true);
	}
	
}
