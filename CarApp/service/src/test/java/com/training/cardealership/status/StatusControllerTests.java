package com.training.cardealership.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StatusControllerTests {
    @Autowired
    StatusController statusController;

    @Test
    void statusControllerReturnsOK(){
        assertEquals("OK", statusController.status());
    }
}