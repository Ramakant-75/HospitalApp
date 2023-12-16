package com.example.hospital.service;

import com.example.hospital.dao.PatientRegRepository;
import com.example.hospital.entity.PatientRegistration;
import com.example.hospital.util.PatientIdGenerator;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

import static com.example.hospital.constant.Constant.*;

@Component
@Log4j2
@Transactional
@Service
public class PatientRegistrationService {

    @Autowired
    private PatientRegRepository patientRegRepository;

    @Autowired
    private PatientIdGenerator patientIdGenerator;

    public HashMap<String, Object> registerPatient(PatientRegistration patientRegistration) {
        HashMap<String,Object> resultMap = new HashMap<>();
        if (null == patientRegistration.getPatientName() || ObjectUtils.isEmpty(patientRegistration.getPatientName())){
            resultMap.put(STATUS,SUCCESS);
            resultMap.put(RESULT,"Please enter your first name and last name");
        } else if (0 == patientRegistration.getContact() || ObjectUtils.isEmpty(patientRegistration.getContact())) {
            resultMap.put(STATUS,SUCCESS);
            resultMap.put(RESULT,"Please enter your contact no.");
        }
        else {
            try {
                patientRegistration.setPatientId(patientIdGenerator.generatePatientId());
                PatientRegistration patientRegistration1 = patientRegRepository.save(patientRegistration);
                resultMap.put(STATUS,SUCCESS);
                resultMap.put(RESULT,patientRegistration1);
            }catch (Exception e){
                resultMap.put(STATUS,ERROR);
                resultMap.put(RESULT,"Exception while registering patient details :" + e.getMessage());
                log.error("Exception while registering patient details :" + e.getMessage());
            }
        }

        return resultMap;
    }

    public PatientRegistration getPatientRegistrationDetails(Long id) {

        return patientRegRepository.findByPatientId(id);
    }
}
