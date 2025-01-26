package com.cricket.stadium_slot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cricket.stadium_slot.entity.StadiumSlot;

public interface StadiumSlotRepository extends JpaRepository<StadiumSlot, Integer>{
    Optional<List<StadiumSlot>> findByStadiumId(int stadiumId);

	Optional<StadiumSlot> findById(int id);
}
