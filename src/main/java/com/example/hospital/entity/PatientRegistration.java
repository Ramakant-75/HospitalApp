package com.example.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "patient_registration")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientRegistration {

    @Id
    @GeneratedValue(generator = "patient_reg_seq_gen")
    @SequenceGenerator(name = "patient_reg_seq_gen",sequenceName = "patient_reg_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "contact")
    private Long contact;

    @Column(name = "disease")
    private String disease;

    @Column(name = "date_of_reg")
    private Timestamp dateOfReg;
}
