package br.com.apontador.places.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import br.com.apontador.places.exception.CoordinatesNotFound;
import br.com.apontador.places.exception.DuplicatePlace;
import br.com.apontador.places.exception.PlaceNotFound;
import br.com.apontador.places.model.Address;
import br.com.apontador.places.model.Place;
import br.com.apontador.places.repository.PlaceRepository;
import br.com.apontador.places.util.StringSimilarity;

@Service
public class PlaceService {
	
	@Autowired GeoService geoService;
	@Autowired PlaceRepository repository;
	
	public void save(Place place) throws CoordinatesNotFound, DuplicatePlace {
		geoService.findGeoLocation(place.getAddress());
		
		Place placeWithSameAddress = repository.findByAddressPlaceId(place.getAddress().getPlaceId());
		
		if (placeWithSameAddress != null && StringSimilarity.isSimilar(place.getName(), placeWithSameAddress.getName())
				&& (place.get_id() == null || placeWithSameAddress.get_id() != place.get_id())) {
			throw new DuplicatePlace("Same address with similar place name");
		}
		
		place.setActive(true);
		repository.save(place);
	}
	
	public Place get(String id) throws PlaceNotFound {
		Place place = repository.findOne(id);
		
		if (place == null) throw new PlaceNotFound();
		
		return place;
	}
	
	public void changeStatus(String id, boolean status) throws PlaceNotFound {
		Place place = get(id);
		
		place.setActive(status);		
		repository.save(place);
	}
	
	public Map<String, Object> search(int page, int rows) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Place> result = new ArrayList<Place>();
		
		
		try {
			Page<Place> list = repository.findAll(new PageRequest(page, rows));
			setPageResult(response, list.getTotalElements(), list.getTotalPages(), list.getNumber());
			result = list.getContent();
		} catch (Exception ex) {
			setPageResult(response, 0, 0, page);
		}
		
		response.put("results", result);
		return response;
		
	}

	public Map<String, Object> search(int page, int rows, String q, Address address, double maxDistance) throws CoordinatesNotFound {
		geoService.findGeoLocation(address);
		
		Point point = new Point(address.getLocation().getX(), address.getLocation().getY());
		Distance distance = new Distance(maxDistance, Metrics.KILOMETERS);
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			Page<GeoResult<Place>> list = repository.findByAddressLocationNear(q, point, distance, new PageRequest(page, rows));
		
			setPageResult(response, list.getTotalElements(), list.getTotalPages(), list.getNumber());
			
			for(GeoResult<Place> geo : list.getContent()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("place", geo.getContent());
				map.put("distance", geo.getDistance());
				result.add(map);
			}
		} catch (Exception ex) {
			setPageResult(response, 0, 0, page);
		}
		
		response.put("results", result);
		return response;
	}
	
	private void setPageResult(Map<String, Object> map, long total, long pages, long page) {
		map.put("found", total);
		map.put("pages", pages);
		map.put("page", page + 1);
	}
}
