package com.cricket.stadium_slot.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StadiumPojo {
	  private int stadiumId;
	  private String stadiumName;
	  private String stadiumLocation;
	  private int stadiumCapacity;
}
