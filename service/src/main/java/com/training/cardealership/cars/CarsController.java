package com.training.cardealership.cars;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/cars")
@RestController
@Validated
public class CarsController {

    @Autowired
    private CarsService carsService;

    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Map<String, String>> addCar(@RequestBody List< @Valid CarDTO> cars) {
        carsService.addCar(cars);
        return new ResponseEntity<>(Map.of("description", "Database updated"), HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<CarDTO>> getCars(@RequestParam Map<String, String> queryParams) {
        return ResponseEntity.ok(carsService.getCars(queryParams));
    }

    @PutMapping("/admin")
    public ResponseEntity<Map<String, String>> updateCar(@RequestBody @Valid CarDTO car) {
        carsService.updateCar(car);
        return ResponseEntity.ok(Map.of("description", "Car updated"));
    }
}
