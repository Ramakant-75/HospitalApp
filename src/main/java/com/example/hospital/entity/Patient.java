package com.example.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;


@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(generator = "patient_seq_gen")
    @SequenceGenerator(name = "patient_seq_gen",sequenceName = "patient_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "patient_age")
    private int patientAge;

    @Column(name = "gender")
    private String gender;

    @Column(name = "disease")
    private String disease;

    @Column(name = "admitted_on")
    @Generated(GenerationTime.INSERT)
    private Timestamp admittedOn;

    @Column(name = "bed_allocated")
    private String bedAllocated;

    @Column(name = "bed_no")
    private String bedNo;

    @Column(name = "room_no")
    private String roomNo;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "status")
    private String status;
}
