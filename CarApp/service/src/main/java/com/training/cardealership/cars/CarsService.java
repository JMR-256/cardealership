package com.training.cardealership.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsService {

    @Autowired
    private CarsRepository carsRepository;

    public CarsService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    public void addCar(List<CarDTO> cars) {
        cars.stream().map(this::mapToEntity).forEach(car ->  carsRepository.save(car));
    }

    private Car mapToEntity(CarDTO car) {
        return new Car(car.getBrand(), car.getModel(), car.getPrice(), car.getYear(), car.getMileage(), car.getColour());
    }
}
