package com.cricket.stadium_slot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cricket.stadium_slot.entity.StadiumSlot;
import com.cricket.stadium_slot.pojo.SlotPojo;
import com.cricket.stadium_slot.pojo.StadiumPojo;
import com.cricket.stadium_slot.pojo.StadiumSlotPojoISC;
import com.cricket.stadium_slot.service.StadiumSlotService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Optional;

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
    @CircuitBreaker(name = "ciremp", fallbackMethod = "fallBackStadium")
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
		Optional<StadiumSlot> stadiumSlot= service.getByStadiumId(id);
		StadiumSlotPojoISC stadiumSlotPojo= new StadiumSlotPojoISC();
		if(stadiumSlot.isPresent()) {
			stadiumSlotPojo.setId(stadiumSlot.get().getId());
			stadiumSlotPojo.setSlot(slot);
			stadiumSlotPojo.setStadium(stadium);
		}
		return new ResponseEntity<StadiumSlotPojoISC>(stadiumSlotPojo, HttpStatus.OK);	
		}

    public ResponseEntity<StadiumSlotPojoISC> fallBackStadium(){
    	return null;
    }
    
    @GetMapping("/stadium/{stadiumId}")
    public Optional<StadiumSlot> getSlotsByStadiumId(@PathVariable int stadiumId) {
        return service.getByStadiumId(stadiumId);
    }

    @PutMapping("/{id}")
    public StadiumSlot updateAvailability(@PathVariable int id, @RequestParam Boolean available) {
        return service.updateAvailability(id, available);
    }
}