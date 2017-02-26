package br.com.apontador.places.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import br.com.apontador.places.dao.PlaceDAO;
import br.com.apontador.places.exception.CoordinatesNotFound;
import br.com.apontador.places.exception.PlaceNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.Place;

@Service
public class PlaceService {
	
	@Autowired GeoService geoService;
	@Autowired PlaceDAO placeDAO;
	
	public void save(Place place) throws CoordinatesNotFound {
		geoService.findGeoLocation(place.getAddress());
		placeDAO.save(place);
	}
	
	public Place get(String id) throws PlaceNotFound {
		Place place = placeDAO.get(id);
		
		if (place == null) throw new PlaceNotFound();
		
		return place;
	}
	
	public void changeStatus(String id, boolean status) throws PlaceNotFound {
		placeDAO.changeStatus(id, status);
	}
	
	public List<Place> search(int start, int rows) {
		return placeDAO.list(start, rows);
	}

	public List<Map> search(int start, int rows, String q, Address address, double maxDistance) throws CoordinatesNotFound {
		geoService.findGeoLocation(address);
		
		Point point = new Point(address.getLocation().getCoordinates()[0], address.getLocation().getCoordinates()[1]);
		
		List<GeoResult<Place>> list = placeDAO.list(start, rows, point, maxDistance, q);
		
		List<Map> result = new ArrayList<Map>();
		for(GeoResult<Place> geo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("place", geo.getContent());
			map.put("distance", geo.getDistance());
			result.add(map);
		}
		
		return result;
	}
}
