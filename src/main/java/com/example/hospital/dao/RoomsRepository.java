package com.example.hospital.dao;

import com.example.hospital.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<Rooms,Long> {
}
