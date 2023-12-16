package com.example.hospital.controller;

import com.example.hospital.service.BedService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Component
@RestController
@Log4j2
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    private BedService roomService;

    @GetMapping("/allrooms")
    public ResponseEntity<HashMap<String,Object>> getAllRoomDetails(){

        log.info("<---------- calling /allrooms API ---------->");
        HashMap<String,Object> map = new HashMap<>();
        map = roomService.getAllRoomDetails();

        log.info("<---------- process complete ---------->");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
