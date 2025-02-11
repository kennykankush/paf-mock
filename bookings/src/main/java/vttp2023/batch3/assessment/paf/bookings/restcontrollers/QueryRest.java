package vttp2023.batch3.assessment.paf.bookings.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/query")
public class QueryRest {

    @Autowired
    private ListingsService listingsService;

    @GetMapping("/uniquecountry")
    public ResponseEntity<Object> getUniqueCountry () {
        List<String> countries = listingsService.fetchUniqueCountries();
        return ResponseEntity.ok().body(countries);
    }
    
    
}
