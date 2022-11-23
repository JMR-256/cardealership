package com.training.cardealership.cars;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/cars")
@RestController
public class CarsController {

    @Autowired
    private CarsService carsService;

    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Map<String, String>> addCar(@RequestBody List<CarDTO> cars) {
        carsService.addCar(cars);
        return new ResponseEntity<>(Map.of("description", "Database updated"), HttpStatus.CREATED);
    }
}
