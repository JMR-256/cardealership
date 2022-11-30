package com.training.cardealership.cars;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CarsRepository extends MongoRepository<Car, String>, ComplexCarRepository {
    boolean existsByBrandIgnoreCaseAndModelIgnoreCase(String brand, String model);
    Optional<Car> findByBrandIgnoreCaseAndModelIgnoreCase(String brand, String model);
}
