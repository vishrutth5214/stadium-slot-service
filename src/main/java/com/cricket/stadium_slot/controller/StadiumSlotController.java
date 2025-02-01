package com.cricket.stadium_slot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cricket.stadium_slot.entity.StadiumSlot;
import com.cricket.stadium_slot.pojo.BookingPojo;
import com.cricket.stadium_slot.pojo.BookingRequestBodyPojo;
import com.cricket.stadium_slot.pojo.PojoSlotStadium;
import com.cricket.stadium_slot.pojo.SlotPojo;
import com.cricket.stadium_slot.pojo.StadiumPojo;
import com.cricket.stadium_slot.pojo.StadiumSlotPojo;
import com.cricket.stadium_slot.pojo.StadiumSlotPojoISC;
import com.cricket.stadium_slot.service.StadiumSlotService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/stadium-slot")
public class StadiumSlotController {

    @Autowired
    private StadiumSlotService service;

    @PostMapping
    public StadiumSlot createStadiumSlot(@RequestBody StadiumSlot stadiumSlot) {
        return service.createStadiumSlot(stadiumSlot);
    }
    
    @GetMapping
    public List<StadiumSlot> getAllStadiumSlots() {
        return service.getAllStadiumSlots();
    }
    
    
    @GetMapping("/isc/{sid}")
	public ResponseEntity<StadiumSlotPojoISC> getStadiumSlot(@PathVariable("sid") int id){
		RestClient restClient = RestClient.create();
		SlotPojo slot = restClient
				.get()
				.uri("http://localhost:1719/slots/"+id)
				.retrieve()
				.body(SlotPojo.class);
		StadiumPojo stadium = restClient
				.get()
				.uri("http://localhost:1718/api/stadiums/"+id)
				.retrieve()
				.body(StadiumPojo.class);
		Optional<StadiumSlot> stadiumSlot= service.getById(id);
		StadiumSlotPojoISC stadiumSlotPojo= new StadiumSlotPojoISC();
		if(stadiumSlot.isPresent()) {
			stadiumSlotPojo.setId(stadiumSlot.get().getId());
			stadiumSlotPojo.setSlot(slot);
			stadiumSlotPojo.setStadium(stadium);
			stadiumSlotPojo.setAvailable(true);
		}
		return new ResponseEntity<StadiumSlotPojoISC>(stadiumSlotPojo, HttpStatus.OK);	
		}
    


    
    @GetMapping("/stadium/{stadiumId}")
    public Optional<StadiumSlot> getSlotsByStadiumId(@PathVariable int stadiumId) {
        return service.getById(stadiumId);
    }
    @GetMapping("/stadiumslot/{stadiumId}/{date}")
    public ResponseEntity<StadiumSlotPojo> getByStadiumId(@PathVariable int stadiumId,@PathVariable LocalDate date){
    	List<StadiumSlot> stads = service.getByStadiumId(stadiumId).orElse(null);
    	StadiumSlotPojo stadiumSlotPojo= new StadiumSlotPojo();
    	StadiumSlotPojo stadiumSlotPojoFinal= new StadiumSlotPojo();
    	stadiumSlotPojo.setStadiumId(stadiumId);
    	stadiumSlotPojoFinal.setStadiumId(stadiumId);
    	List<PojoSlotStadium> slots=new ArrayList();
    	List<PojoSlotStadium> slotsFinal=new ArrayList();
        for(StadiumSlot row:stads) {
    		int  id=row.getSlotId();
    		PojoSlotStadium ss=new PojoSlotStadium();
    		ss.setStadiumSlotId(row.getId());
    	    RestClient restClient = RestClient.create();
		    SlotPojo slot = restClient
				.get()
				.uri("http://localhost:1719/slots/"+id)
				.retrieve()
				.body(SlotPojo.class);
		    ss.setSlot(slot);
		    slots.add(ss);
    	}
        stadiumSlotPojo.setSlots(slots);
        WebClient webClient = WebClient.create();
        // Assuming BookingPojo is a defined class
        Mono<List<BookingPojo>> bookingsMono = webClient
                .get()
                .uri("http://localhost:1721/bookings/"+stadiumId+"/"+date)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BookingPojo>>() {});

        // Blocking for simplicity (avoid in production)
        List<BookingPojo> bookings = bookingsMono.block();
		for(PojoSlotStadium stadslot:stadiumSlotPojo.getSlots()) {
			boolean found=false;
			for(BookingPojo booking:bookings) {
				  if(stadslot.getStadiumSlotId()==booking.getStadiumSlotId()) {
					  found=true;
					  break;
				  } 
			}
			if(!found) {
				slotsFinal.add(stadslot);
			}
		}
		stadiumSlotPojoFinal.setSlots(slotsFinal);
    	return new ResponseEntity<StadiumSlotPojo>(stadiumSlotPojoFinal,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public StadiumSlot updateAvailability(@PathVariable int id, @RequestParam Boolean available) {
        return service.updateAvailability(id, available);
    }
}