package com.training.cardealership.cars;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarsRepository extends MongoRepository<Car, String> {
}
