package com.example.hospital.dao;

import com.example.hospital.entity.FloorStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FloorStructureRepository extends JpaRepository<FloorStructure,Long> {

    @Query(value = "SELECT sum(no_of_avail_beds) FROM floor_structure WHERE room_type =:roomType",nativeQuery = true)
    int getNoOfAvailableBeds(String roomType);

}
