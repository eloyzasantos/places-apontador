package br.com.apontador.places.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.apontador.places.exception.PlaceNotFound;
import br.com.apontador.places.model.Place;

@Repository
public class PlaceDAO {

	@Autowired MongoTemplate mongoTemplate;
	
	public void save(Place place) {
		place.setActive(true);
		mongoTemplate.save(place, "place");
	}
	
	public void changeStatus(String id, boolean status) throws PlaceNotFound {
		Place place = get(id);
		
		if (place == null) throw new PlaceNotFound();
		
		place.setActive(status);
		mongoTemplate.save(place, "place");
	}
	
	public Place get(String id) {
		return mongoTemplate.findById(new ObjectId(id), Place.class);
	}
	
	public List<GeoResult<Place>> list(int start, int rows, Point point, double maxDistance, String q) {
		NearQuery query = NearQuery.near(point)
				.maxDistance(maxDistance, Metrics.KILOMETERS)
				.spherical(true)
				.skip(start-1)
				.num(rows)
				.query(createQuery().addCriteria(new Criteria().where("name").regex(q)));
		GeoResults<Place> result = mongoTemplate.geoNear(query, Place.class);
		return result.getContent();
	}
	
	public List<Place> list(int start, int rows) {
		return mongoTemplate.find(createQuery(), Place.class);
	}
	
	private Query createQuery() {
		Criteria criteria = new Criteria().where("active").is(true);
		return new Query(criteria);
	}
}
