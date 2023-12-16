package com.example.hospital.service;

import com.example.hospital.dao.BedsRepository;
import com.example.hospital.dao.FloorStructureRepository;
import com.example.hospital.dao.PatientRepository;
import com.example.hospital.entity.Beds;
import com.example.hospital.entity.Patient;
import com.example.hospital.entity.PatientRegistration;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
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

    @Autowired
    private BedsRepository bedsRepository;

    @Autowired
    private BedService bedService;

    @Autowired
    private FloorStructureRepository floorStructureRepository;

    @Autowired
    private PatientRegistrationService patientRegistrationService;


    public HashMap<String, Object> getPatientDetails() {

        HashMap<String,Object> resultMap = new HashMap<>();
        List<Patient> patientList;
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
        String patientAllocatedBed = null;
            for (Patient patientObj : patient){
                if (isValidPatient(patientObj)) {
                    if (!ObjectUtils.isEmpty(patientRepository.findPatientId(patientObj.getPatientId()))) {
                        resultMap.put(STATUS, SUCCESS);
                        resultMap.put(RESULT, "Patient with id : " + patientObj.getPatientId() + " already exists!");
                    } else {
                        if (patientObj.getRoomNo().contains("SD")) {
                            patientObj.setRoomType("Super Deluxe");
                        } else {
                            patientObj.setRoomType("General");
                        }
                        if (floorStructureRepository.getNoOfAvailableBeds(patientObj.getRoomType()) > 0) {
                            if (bedService.isBedAvailable(patientObj.getBedNo())) {
                                try {
                                    patientObj.setBedAllocated("Y");
                                    patientObj.setStatus("ADMITTED");
                                    Patient patient1 = patientRepository.save(patientObj);
                                    resultMap.put(STATUS,SUCCESS);
                                    resultMap.put("Message","Patient admitted");
                                    resultMap.put(RESULT,patient1);
                                } catch (Exception e) {
                                    log.error("Exception while saving patient details : " + e.getMessage());
                                    resultMap.put(STATUS, ERROR);
                                    resultMap.put(RESULT, e.getMessage());
                                }
                            } else {
                                resultMap.put(STATUS, SUCCESS);
                                resultMap.put(RESULT, "Bed no : " + patientObj.getBedNo() + " is already allotted");
                                log.info("Bed no : " + patientObj.getBedNo() + " is already allotted");
                            }
                        } else {
                            // add logic to keep patient in waiting queue
                        }
                    }
                }
                else {
                    resultMap.put(STATUS,SUCCESS);
                    resultMap.put(RESULT,"Patient details do not match, please check once again");

                }
            }

        return resultMap;
    }

    private boolean isValidPatient(Patient patient) {

        boolean isValid = false;
        PatientRegistration patientRegistrationDetails = patientRegistrationService.getPatientRegistrationDetails(patient.getPatientId());
        if (null != patientRegistrationDetails) {
            if (patientRegistrationDetails.getPatientId() == patient.getPatientId() && patientRegistrationDetails.getPatientName().equalsIgnoreCase(patient.getPatientName()) && patientRegistrationDetails.getAge() == patient.getPatientAge()) {
                isValid = true;
            }
        }

        return isValid;
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
                bedService.updateBedAllocaion(id);
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

    private void populateBed(Patient patient) {

        Beds beds = new Beds();
        try {
            beds.setPatientId(patient.getPatientId());
            beds.setBedNo(patient.getBedNo());
            beds.setIsOccupied("Y");
            beds.setRoomNo(patient.getRoomNo());
            if (patient.getBedNo().contains("X")){
                beds.setRoomType("Deluxe Room");
            }
            if (patient.getBedNo().contains("Z")){
                beds.setRoomType("Super Deluxe Room");
            }
            else {
                beds.setRoomType("General Room");
            }

            bedsRepository.save(beds);

        }catch (Exception e){
            log.error("Exception : " +e);
        }

    }

    private boolean checkBedAvailability(Patient patient) {

        boolean isAvailable = false;
        int available = bedsRepository.findAvailableBeds(patient.getBedNo());

        if (available == 0){
            isAvailable = true;
        }

        return isAvailable;
    }
}
