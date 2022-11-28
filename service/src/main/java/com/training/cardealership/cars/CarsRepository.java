package com.training.cardealership.cars;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarsRepository extends MongoRepository<Car, String>, ComplexCarRepository {
    boolean existsByBrandIgnoreCaseAndModelIgnoreCase(String brand, String model);
}
