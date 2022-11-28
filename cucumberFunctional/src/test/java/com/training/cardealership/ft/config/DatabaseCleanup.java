package com.training.cardealership.ft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.event.annotation.AfterTestClass;

@Configuration
@Profile("local")
public class DatabaseCleanup {

    @Autowired
    MongoTemplate mongoTemplate;

    @AfterTestClass
    public void afterTest() {
        mongoTemplate.getDb().drop();
    }
}
