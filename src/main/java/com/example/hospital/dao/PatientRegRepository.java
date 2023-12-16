package com.example.hospital.dao;

import com.example.hospital.entity.PatientRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface PatientRegRepository extends JpaRepository<PatientRegistration,Long> {

    @Query(value = "SELECT MAX(patient_id) FROM patient_registration",nativeQuery = true)
    Long lastPatientId();

    @Query(value = "SELECT * FROM patient_registration WHERE patient_id =:id",nativeQuery = true)
    PatientRegistration findByPatientId(Long id);
}
