package com.training.cardealership.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ComplexCarRepositoryImpl implements ComplexCarRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Car> findByQuery(String brand, String model, String price, String year, String mileage, String colour) {
        Query query = new Query();

        if (brand != null) {
            query.addCriteria(Criteria.where("brand").regex(brand, "i"));
        }
        if (model != null) {
            query.addCriteria(Criteria.where("model").regex(model, "i"));
        }
        if (price != null) {
            int intPrice = Integer.parseInt(price);
            query.addCriteria(Criteria.where("price").lte(intPrice));
        }
        if (year != null) {
            int intYear = Integer.parseInt(year);
            query.addCriteria(Criteria.where("year").is(intYear));
        }
        if (mileage != null) {
            int intMileage = Integer.parseInt(mileage);
            query.addCriteria(Criteria.where("mileage").lte(intMileage));
        }
        if (colour != null) {
            query.addCriteria(Criteria.where("colour").regex(colour, "i"));
        }
        return mongoTemplate.find(query, Car.class);
    }
}
