package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vttp2023.batch3.assessment.paf.bookings.models.Accomodation;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class ListingsController {

	@Autowired
	private ListingsService listingsService;

	//TODO: Task 2
	@GetMapping("")
	public String showHome(Model model) {

		List<String> countries = listingsService.fetchUniqueCountries();

		model.addAttribute("countries", countries);
		model.addAttribute("accomodation", new Accomodation());

		return "Home";
	}

	@PostMapping("/submit")
	public Object submitAccomodation(@ModelAttribute @Valid Accomodation accomodation, BindingResult results, Model model) {
		
		
		if (results.hasErrors()){
			List<String> countries = listingsService.fetchUniqueCountries();
        	model.addAttribute("countries", countries);
			return "Home";
		}

		String params = "?country=" + accomodation.getCountry() +
                    	"&size=" + accomodation.getSize() +
                    	"&minPrice=" + accomodation.getMinPrice() +
                    	"&maxPrice=" + accomodation.getMaxPrice();

		return "redirect:/search" + params;
	}
	

	//TODO: Task 3
	@GetMapping("/search")
	public String search(
	@RequestParam String country,
    @RequestParam Integer size,
    @RequestParam Double minPrice,
    @RequestParam Double maxPrice, 
	Model model) {

		Accomodation accomodation = new Accomodation(country, size, minPrice, maxPrice);

		List<Document> result = listingsService.queriedAccomodation(accomodation);

		model.addAttribute("result", result);

		// return ResponseEntity.ok().body(result); if wanna return REST, turn the return to Object instead of String
		return "Results";
	}
	


	//TODO: Task 4
	@GetMapping("/details")
	public String getApt(@RequestParam String id, Model model) {

		Document apt = listingsService.queryApartment(id);
		model.addAttribute("apartment", apt);
		model.addAttribute("booking", new Booking());
		model.addAttribute("acc_id", id);

		return "Details";


	}
	
	//TODO: Task 5
	@PostMapping("/book")
	public String bookApt(@ModelAttribute Booking booking, Model model) {

		booking.setResv_id(UUID.randomUUID().toString().replace("-", "").substring(0, 8));

		int vacancy = listingsService.getVacancy(booking.getAcc_id());
		System.out.println(vacancy);
		System.out.println(booking.getDuration());

		if (booking.getDuration() < vacancy){
			
			listingsService.saveBooking(booking);
			listingsService.updateVacancy(vacancy - booking.getDuration(), booking.getAcc_id());
		} else {
			return "Details";
		}

		model.addAttribute("booking", booking);

		return "Booked";
	}




}
