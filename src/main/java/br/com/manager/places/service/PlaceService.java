package br.com.manager.places.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import br.com.manager.places.domain.Place;
import br.com.manager.places.exception.CoordinatesNotFound;
import br.com.manager.places.exception.DuplicatePlace;
import br.com.manager.places.exception.PlaceNotFound;
import br.com.manager.places.model.Address;
import br.com.manager.places.model.Paginator;
import br.com.manager.places.model.ResponseList;
import br.com.manager.places.model.ResponseSearchNear;
import br.com.manager.places.model.ResultSearchNear;
import br.com.manager.places.repository.PlaceRepository;
import br.com.manager.places.util.StringSimilarity;

@Service
public class PlaceService {
	
	@Autowired GeoService geoService;
	@Autowired PlaceRepository repository;
	
	public void save(Place place) throws CoordinatesNotFound, DuplicatePlace, PlaceNotFound {
		geoService.findGeoLocation(place.getAddress());
		
		if (place.get_id() != null && !repository.exists(place.get_id())) throw new PlaceNotFound();
		
		Place placeWithSameAddress = repository.findByAddressPlaceId(place.getAddress().getPlaceId());
		
		if (placeWithSameAddress != null && StringSimilarity.isSimilar(place.getName(), placeWithSameAddress.getName())
				&& (place.get_id() == null || !placeWithSameAddress.get_id().equals(place.get_id()))) {
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
	
	public ResponseList list(int page, int rows) {
		ResponseList response = new ResponseList();
		List<Place> result = new ArrayList<Place>();
		
		
		try {
			Page<Place> list = repository.findAll(new PageRequest(page, rows));
			setPageResult(response, list.getTotalElements(), list.getTotalPages(), list.getNumber());
			result = list.getContent();
		} catch (Exception ex) {
			setPageResult(response, 0, 0, page);
		}
		
		response.setResults(result);
		return response;
		
	}

	public ResponseSearchNear search(int page, int rows, String q, Address address, double maxDistance) throws CoordinatesNotFound {
		geoService.findGeoLocation(address);
		
		Point point = new Point(address.getLocation().getX(), address.getLocation().getY());
		Distance distance = new Distance(maxDistance, Metrics.KILOMETERS);
		
		ResponseSearchNear response = new ResponseSearchNear();
		List<ResultSearchNear> results = new ArrayList<ResultSearchNear>();
		
		try {
			Page<GeoResult<Place>> list = repository.findByAddressLocationNear(q, point, distance, new PageRequest(page, rows));
		
			setPageResult(response, list.getTotalElements(), list.getTotalPages(), list.getNumber());
			
			for(GeoResult<Place> geo : list.getContent()) {
				ResultSearchNear result = new ResultSearchNear();
				result.setDistance(geo.getDistance());
				result.setPlace(geo.getContent());
				results.add(result);
			}
		} catch (Exception ex) {
			setPageResult(response, 0, 0, page);
		}
		
		response.setResults(results);
		return response;
	}
	
	private void setPageResult(Paginator pag, long total, int pages, int page) {
		pag.setFound(total);
		pag.setPages(pages);
		pag.setPage(page + 1);
	}
}
