package com.example.hospital.dao;

import com.example.hospital.entity.Beds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BedsRepository extends JpaRepository<Beds,Long> {
    @Query(value = "select count(*) from beds where bed_no =:bedNo and is_occupied ='Y'",nativeQuery = true)
    int findAvailableBeds(@Param(value = "bedNo") String bedNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "update beds set is_occupied ='N' where patient_id =:patientId",nativeQuery = true)
    void updateBeds(@Param("patientId") Long patientId);
}
