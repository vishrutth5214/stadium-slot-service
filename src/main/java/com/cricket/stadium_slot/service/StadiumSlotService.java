package com.cricket.stadium_slot.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricket.stadium_slot.entity.StadiumSlot;
import com.cricket.stadium_slot.repository.StadiumSlotRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StadiumSlotService {

    @Autowired
    private StadiumSlotRepository repository;

    public StadiumSlot createStadiumSlot(StadiumSlot stadiumSlot) {
        return repository.save(stadiumSlot);
    }

    public List<StadiumSlot> getAllStadiumSlots() {
        return repository.findAll();
    }

    public Optional<StadiumSlot> getById(int stadiumId) {
    	
        return repository.findById(stadiumId);
    }
    public Optional<List<StadiumSlot>> getByStadiumId(int stadiumId){
    	System.out.println(repository.findByStadiumId(stadiumId));
    	return repository.findByStadiumId(stadiumId);
    }

    public StadiumSlot updateAvailability(int id, Boolean available) {
        StadiumSlot stadiumSlot = repository.findById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
        stadiumSlot.setAvailable(available);
        return repository.save(stadiumSlot);
    }
}