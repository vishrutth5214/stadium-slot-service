package com.cricket.stadium_slot.pojo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingPojo {
   int bookingId;
   int stadiumSlotId;
   private int userId; // One-to-One with UserRoles
   private LocalDate bookingDate;
   private String status;
}
