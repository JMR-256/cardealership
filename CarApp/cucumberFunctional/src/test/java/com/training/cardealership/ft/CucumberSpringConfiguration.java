package com.training.cardealership.ft;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = com.training.cardealership.CarDealershipApplication.class)
public class CucumberSpringConfiguration {
}
