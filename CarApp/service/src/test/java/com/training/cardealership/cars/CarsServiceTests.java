package com.training.cardealership.cars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTests {

    @InjectMocks
    private CarsService carsService;

    @Mock
    private CarsRepository carsRepository;

    @Test
    void add_car_callsRepository_correctNumber_of_time() {
        List<CarDTO> cars = List.of(new CarDTO("BMW", "X5", 80000, 2022, 10000, "Space Grey"));
        carsService.addCar(cars);
        Mockito.verify(carsRepository, times(1)).save(new Car("BMW", "X5", 80000, 2022, 10000, "Space Grey"));
    }
}
