package com.example.hospital.service;

import com.example.hospital.dao.PatientRepository;
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
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;


    public HashMap<String, Object> getPatientDetails() {

        HashMap<String,Object> resultMap = new HashMap<>();
        List<Patient> patientList = new ArrayList<>();
        try {
            patientList = patientRepository.findAll();
            resultMap.put(STATUS,SUCCESS);
            resultMap.put(RESULT,patientList);
        }catch (Exception e){
            log.error("Exception : " + e);
            resultMap.put(STATUS,ERROR);
            resultMap.put(RESULT,e);
        }


        return resultMap;
    }

    public HashMap<String, Object> getPatientDetailsByParam(Long id, String patientName) {

        HashMap<String,Object> resultMap = new HashMap<>();
        Optional<Patient> patient = Optional.of(new Patient());
        List<Patient> patientList = new ArrayList<>();

        try {

            if (null != id) {
                patient = patientRepository.findById(id);
                resultMap.put(STATUS,SUCCESS);
                resultMap.put(RESULT,patient);
            }
            if (null != patientName) {
                patientList = patientRepository.findPatientByName(patientName);
                resultMap.put(STATUS,SUCCESS);
                resultMap.put(RESULT,patientList);
            }
        }catch (Exception e){
            log.error("Exception : " +e);

            resultMap.put(STATUS,ERROR);
            resultMap.put(RESULT,e);
        }



        return resultMap;
    }

    public HashMap<String, Object> addPatients(List<Patient> patient) {

        HashMap<String,Object> resultMap = new HashMap<>();
        List<Patient> patientList = new ArrayList<>();

        try {
            patientList = patientRepository.saveAll(patient);
            resultMap.put(STATUS,SUCCESS);
            resultMap.put(RESULT,patientList);
        }catch (Exception e){
            log.error("Exception : " + e);
            resultMap.put(STATUS,ERROR);
            resultMap.put(RESULT,e);
        }


        return resultMap;
    }

    public HashMap<String, Object> removePatient(Long id) {

        HashMap<String,Object> resultMap = new HashMap<>();
        int count = patientRepository.getCount(id);
        if (count == 0){
            resultMap.put(STATUS,SUCCESS);
            resultMap.put(RESULT,"Patient with id " + id + " does not exist");

            return resultMap;
        }
        else {

            try {
                patientRepository.deleteById(id);
                resultMap.put(STATUS, SUCCESS);
                resultMap.put(RESULT, "Patient with id " + id + " has been removed");
            } catch (Exception e) {
                log.error("Exception : " + e);
                resultMap.put(STATUS, ERROR);
                resultMap.put(RESULT, e);
            }
        }

        return resultMap;
    }
}
