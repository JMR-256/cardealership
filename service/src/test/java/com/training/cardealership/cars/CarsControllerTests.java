package com.training.cardealership.cars;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

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

    @Test
    void getCar_returns200() {
        ResponseEntity<?> response = carsController.getCars(new HashMap<>());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void putCar_returns200() {
        ResponseEntity<?> response = carsController.updateCar(new CarDTO("Ford", "Fiesta", 10000, 2022, 2000, "red"));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void putCar_returnsCorrectBody() {
        ResponseEntity<Map<String, String>> response = carsController.updateCar(new CarDTO("Ford", "Fiesta", 10000, 2022, 2000, "red"));
        Assertions.assertTrue(response.getBody().containsKey("description"));
        Assertions.assertEquals("Car updated" ,response.getBody().get("description"));
    }

    @Test
    void deleteCar_returns204() {
        ResponseEntity<?> response = carsController.deleteCar(any(), any());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private  ResponseEntity<Map<String, String>> carsControllerAdder() {
        return carsController.addCar(List.of(new CarDTO("BMW", "X5", 800000, 2022, 10000, "Space Grey")));
    }
}
