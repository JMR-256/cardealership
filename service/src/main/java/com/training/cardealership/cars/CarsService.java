package com.training.cardealership.cars;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.training.cardealership.exceptions.CarExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
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
            cars.stream().map(this::mapToEntity).forEach(car -> {
                if (carsRepository.existsByBrandIgnoreCaseAndModelIgnoreCase(car.getBrand(), car.getModel())) {
                    throw new CarExistsException();
                }
                carsRepository.save(car);
            });
    }

    private Car mapToEntity(CarDTO car) {
        return new Car(car.getBrand(), car.getModel(), car.getPrice(), car.getYear(), car.getMileage(), car.getColour());
    }
}
