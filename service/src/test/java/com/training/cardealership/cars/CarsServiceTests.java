package com.training.cardealership.cars;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
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

    @Test
    void throws_exception_when_data_not_valid() {
        CarDTO carDTO = new CarDTO("BMW", "X5", 80000, 2022, 10000, "Space Grey");
        Car car =  new Car("BMW", "X5", 80000, 2022, 10000, "Space Grey");

        Mockito.when(carsRepository.save(car)).thenThrow(ConstraintViolationException.class);
        List<CarDTO> cars = List.of(carDTO);
        Assertions.assertThrows(ConstraintViolationException.class, () ->carsService.addCar(cars));
        Mockito.verify(carsRepository, times(1)).save(car);
    }
}
