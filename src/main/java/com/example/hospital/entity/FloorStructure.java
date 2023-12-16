package com.example.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "floor_structure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloorStructure {

    @Id
    @GeneratedValue(generator = "floor_structure_gen")
    @SequenceGenerator(name = "floor_structure_gen",sequenceName = "floor_structure_seq",allocationSize = 1)
    private Long id;

    @Column(name = "floor_no")
    private String floorNo;

    @Column(name = "room_no")
    private String roomNo;

    @Column(name = "no_of_beds")
    private int noOfBeds;

    @Column(name = "no_of_avail_beds")
    private int noOfAvailableBeds;
}
