package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import vttp2023.batch3.assessment.paf.bookings.models.Accomodation;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//TODO: Task 2

	// db.listings.distinct(
	// 	"address.country"
	// 	);

	public List<String> fetchUniqueCountries(){

		MongoCollection<Document> mongoCollection = mongoTemplate.getCollection("listings");
		List<String> fetchUniqueCountries = mongoCollection.distinct("address.country", String.class).into(new ArrayList<>());

		return fetchUniqueCountries;

	}

	//TODO: Task 3
	
	// db.listings.find(
	// 	{
	// 	  "address.country": "Australia",
	// 	  "accommodates": { "$lte": 2 },
	// 	  "$and": [
	// 		{ "price": { "$gte": 200.0 } },
	// 		{ "price": { "$lte": 500.0 } }
	// 	  ]
	// 	}
	//   )


	// ------------------------------------------------

	// 	db.listings.aggregate([
	//   { 
	//     $match: {
	//       "address.country": "Australia",
	//       "accommodates": { "$lte": 2 },
	//       "$and": [
	//         { "price": { "$gte": 100.0 } },
	//         { "price": { "$lte": 102.0 } }
	//       ]
	//     }
	//   },
	//   {
	//     $project: {
	//       _id: 1,
	//       name: 1,
	//       price: 1,
	//       picture_url: "$images.picture_url"
	//     }
	//   }
	// ])

	// -----------------------------------------------

	public List<Document> queriedAccomodation(Accomodation accomodation){

		Criteria criteria = Criteria.where("address.country").is(accomodation.getCountry())
									.and("accommodates").lte(accomodation.getSize())
									.andOperator(
									Criteria.where("price").gte(accomodation.getMinPrice()),
    								Criteria.where("price").lte(accomodation.getMaxPrice())
									);

		// Query query = new Query(criteria);
		// return mongoTemplate.find(query, Document.class, "listings");

		MatchOperation matchStage = Aggregation.match(criteria);
		ProjectionOperation projectStage = Aggregation.project().andInclude("_id","name", "price").and("images.picture_url").as("picture_url");

		Aggregation aggregation = Aggregation.newAggregation(matchStage, projectStage);
		AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "listings", Document.class);

		return results.getMappedResults();
		
	}

	//----------------------------------------

	// 	db.listings.find(
	//   { "_id": ObjectId("65f22a1bce482") },
	//   { "_id": 1, "description": 1, "address": 1, "price": 1, "amenities": 1, "images": 1 }
	// )

	//----------------------------------------

	//TODO: Task 4

	public Document queryApartment(String id){

		Criteria criteria = Criteria.where("_id").is(id);

		Query query = new Query(criteria);

		query.fields().include("_id", "description", "address", "price", "amenities", "images");
		return mongoTemplate.findOne(query, Document.class, "listings");

	}

	//TODO: Task 5

	public int getVacancy(String id){

		return jdbcTemplate.queryForObject(SqlQuery.SQL_GET_VACANCY, Integer.class, id);

	}

	public int saveBooking(Booking booking){
		return jdbcTemplate.update(SqlQuery.SQL_SAVE_BOOKING, booking.getResv_id(), booking.getName(), booking.getEmail(), booking.getAcc_id(), booking.getArrivalDate(), booking.getDuration() );
	}

	public int updateVacancy(int duration, String id){
		return jdbcTemplate.update(SqlQuery.SQL_UPDATE_VACANCY, duration, id);
	}


}
