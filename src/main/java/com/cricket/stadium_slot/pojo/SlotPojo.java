package com.cricket.stadium_slot.pojo;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SlotPojo {
	
	    private int id;
	    private LocalTime startTime;
	    private LocalTime endTime;
}
