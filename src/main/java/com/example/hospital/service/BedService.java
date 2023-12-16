package com.example.hospital.service;

import com.example.hospital.dao.BedsRepository;
import com.example.hospital.dao.PatientRepository;
import com.example.hospital.entity.Beds;
import com.example.hospital.entity.Patient;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.example.hospital.constant.Constant.*;

@Service
@Transactional
@Log4j2
public class BedService {

    @Autowired
    private BedsRepository bedsRepository;

    @Autowired
    private PatientRepository patientRepository;

    public HashMap<String, Object> getAllRoomDetails() {

        HashMap<String,Object> resultMap = new HashMap<>();
        List<Beds> roomsList = new ArrayList<>();
        try {
            roomsList = bedsRepository.findAll();
            resultMap.put(STATUS,SUCCESS);
            resultMap.put(RESULT,roomsList);
        }catch (Exception e){
            log.error("Exception : " + e);
            resultMap.put(STATUS,ERROR);
            resultMap.put(RESULT,e);
        }

        return resultMap;
    }

    public void updateBedAllocaion(Long id) {

        Patient patient = patientRepository.findPatientId(id);

        log.info("patient id : " + patient.getPatientId());
        bedsRepository.updateBeds(patient.getPatientId());

    }

    public boolean isBedAvailable(String bedNo){
        boolean isBedAvailable = false;

        int count = patientRepository.getBedCount(bedNo);
        if (count != 0){
            isBedAvailable = true;
        }

        return isBedAvailable;
    }
}
