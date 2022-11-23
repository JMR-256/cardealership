package com.training.cardealership.cars;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CarsControllerTests {

    @InjectMocks
    private CarsController carsController;

    @Mock
    private CarsService carsService;

    @Test
    void addCar_returns201() {
        ResponseEntity<?> response = carsControllerAdder();
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    void addCar_returnsCorrectBody() {
        ResponseEntity<Map<String, String>> response = carsControllerAdder();
        Assertions.assertTrue(response.getBody().containsKey("description"));
        Assertions.assertEquals("Database updated" ,response.getBody().get("description"));
    }

    private  ResponseEntity<Map<String, String>> carsControllerAdder() {
        return carsController.addCar(List.of(new CarDTO("BMW", "X5", 800000, 2022, 10000, "Space Grey")));
    }
}
