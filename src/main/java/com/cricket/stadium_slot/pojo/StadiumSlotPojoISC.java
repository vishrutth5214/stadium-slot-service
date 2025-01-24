package com.cricket.stadium_slot.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StadiumSlotPojoISC {
        private int id;
        SlotPojo slot;
        StadiumPojo stadium;
        
}
