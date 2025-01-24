package com.cricket.stadium_slot.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SlotPojo {
	
	    private int id;
	    private LocalDateTime startTime;
	    private LocalDateTime endTime;
}
