package com.training.cardealership.cars;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface ComplexCarRepository {
    List<Car> findByQuery(String brand, String model, String price, String year, String mileage, String colour);
}
