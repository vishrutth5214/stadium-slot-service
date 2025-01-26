package com.cricket.stadium_slot.pojo;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingRequestBodyPojo {

	int stadiumId;
	LocalDate date;
}
