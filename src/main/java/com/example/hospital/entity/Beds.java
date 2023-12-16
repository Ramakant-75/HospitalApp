package com.example.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "beds")
@NoArgsConstructor
@AllArgsConstructor
public class Beds {

    @Id
    @GeneratedValue(generator = "beds_seq_gen")
    @SequenceGenerator(name = "beds_seq_gen",sequenceName = "beds_seq",allocationSize = 1)
    private Long id;

    @Column(name = "room_no")
    private String roomNo;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "bed_no")
    private String bedNo;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "is_occupied")
    private String isOccupied;

}
