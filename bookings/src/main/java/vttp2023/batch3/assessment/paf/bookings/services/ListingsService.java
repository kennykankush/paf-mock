package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.models.Accomodation;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingsRepository;
	
	//TODO: Task 2

	public List<String> fetchUniqueCountries(){
		return listingsRepository.fetchUniqueCountries();
	}

	//TODO: Task 3

	public List<Document> queriedAccomodation(Accomodation accomodation){
		return listingsRepository.queriedAccomodation(accomodation);
		
	}

	//TODO: Task 4

	public Document queryApartment(String id){
		return listingsRepository.queryApartment(id);
		
	}
	

	//TODO: Task 5


	public int getVacancy(String id){
		return listingsRepository.getVacancy(id);
	}

	public int saveBooking(Booking booking){
		return listingsRepository.saveBooking(booking);
	}

	public int updateVacancy(int duration, String id){
		return listingsRepository.updateVacancy(duration, id);
	}


}
