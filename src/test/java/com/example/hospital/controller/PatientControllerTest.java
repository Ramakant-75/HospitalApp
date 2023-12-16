package com.example.hospital.controller;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class PatientControllerTest {

    @Test
    public void getPatientDetailsTest(){
        log.info("getPatientDetailsTest:");
        Assertions.assertEquals("test","test");
    }
}
