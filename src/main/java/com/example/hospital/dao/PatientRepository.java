package com.example.hospital.dao;

import com.example.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query(value = "select * from patient where patient_name =:patientName",nativeQuery = true)
    List<Patient> findPatientByName(@Param(value = "patientName") String patientName);

    @Query(value = "select count(*) from patient where id =:id",nativeQuery = true)
    int getCount(@Param("id") Long id);
}
