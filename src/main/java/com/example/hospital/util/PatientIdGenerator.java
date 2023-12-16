package com.example.hospital.util;

import com.example.hospital.dao.PatientRegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

@Component
public class PatientIdGenerator {

    @Autowired
    private PatientRegRepository patientRegRepository;

    public int generatePatientId(){
        Long patientId = patientRegRepository.lastPatientId();
        int finalPatientId = 0;

        if (CollectionUtils.isEmpty(Collections.singleton(patientId)) || patientId == null){
            finalPatientId = 1000;
        }
        else {
            finalPatientId = patientId.intValue();
        }

        return finalPatientId + 1;

    }


}
