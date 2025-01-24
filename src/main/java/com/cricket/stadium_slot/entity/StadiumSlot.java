package com.cricket.stadium_slot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stadium_slot")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="stadium_id")
    private int stadiumId;

    @Column(name="slot_id")
    private int slotId;

    @Column(name="available")
    private Boolean available;
}