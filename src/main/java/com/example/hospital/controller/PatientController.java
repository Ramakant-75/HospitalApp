package com.example.hospital.controller;

import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Component
@RestController
@Log4j2
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patientdetails")
    public ResponseEntity<HashMap<String,Object>> getPatientDetails(@RequestParam(name = "id",required = false)Long id,
                                                                    @RequestParam(value = "patientName",required = false)String patientName){

        log.info("<---------- calling /getPatientDetails API ---------->");
        HashMap<String,Object> map = new HashMap<>();
        if (id != null || patientName != null){
            map = patientService.getPatientDetailsByParam(id,patientName);
        }
        else {
            map = patientService.getPatientDetails();
        }
        log.info("<---------- process complete ---------->");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/addpatient")
    public ResponseEntity<HashMap<String,Object>> addPatient(@RequestBody List<Patient> patient){

        log.info("<----------- calling /addPatient API ---------->");
        HashMap<String,Object> map = new HashMap<>();
        map = patientService.addPatients(patient);

        log.info("<---------- process complete ---------->");

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/removepatient/{id}")
    public ResponseEntity<HashMap<String,Object>> removePatient(@PathVariable Long id){

        log.info("<----------- calling /removePatient API ---------->");
        HashMap<String,Object> map = new HashMap<>();
        map = patientService.removePatient(id);

        log.info("<---------- process complete ---------->");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}
