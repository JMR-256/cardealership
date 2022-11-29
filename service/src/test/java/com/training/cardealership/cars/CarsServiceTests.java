package com.training.cardealership.cars;

import com.training.cardealership.exceptions.CarExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTests {

    @InjectMocks
    private CarsService carsService;

    @Mock
    private CarsRepository carsRepository;

    private static final Car EXAMPLE_CAR = new Car("BMW", "X5", 80000, 2022, 10000, "Space Grey");
    private static final CarDTO EXAMPLE_CAR_DTO = new CarDTO("BMW", "X5", 80000, 2022, 10000, "Space Grey");

    @Test
    void add_car_callsRepository_correctNumber_of_time() {
        List<CarDTO> cars = List.of(EXAMPLE_CAR_DTO);
        carsService.addCar(cars);
        Mockito.verify(carsRepository, times(1)).save(EXAMPLE_CAR);
    }

    @Test
    void throws_exception_when_data_not_valid() {
        Mockito.when(carsRepository.save(EXAMPLE_CAR)).thenThrow(ConstraintViolationException.class);
        List<CarDTO> cars = List.of(EXAMPLE_CAR_DTO);
        Assertions.assertThrows(ConstraintViolationException.class, () ->carsService.addCar(cars));
        Mockito.verify(carsRepository, times(1)).save(EXAMPLE_CAR);
    }

    @Test
    void throws_correct_exception_when_duplicate_car_exists() {
        Mockito.when(carsRepository.existsByBrandIgnoreCaseAndModelIgnoreCase(EXAMPLE_CAR_DTO.getBrand(), EXAMPLE_CAR_DTO.getModel())).thenReturn(false).thenReturn(true);

        Assertions.assertThrows(CarExistsException.class, () ->carsService.addCar(List.of(EXAMPLE_CAR_DTO, EXAMPLE_CAR_DTO)));
    }
    @Test
    void findCars_calls_repo_findAll_whenParamsEmpty() {
    Mockito.when(carsRepository.findAll(any(Sort.class))).thenReturn(List.of(EXAMPLE_CAR));
     List<CarDTO> responseCars = carsService.getCars(new HashMap<>());
     Assertions.assertEquals(List.of(EXAMPLE_CAR_DTO), responseCars);
     Mockito.verify(carsRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void findCars_calls_repo_findByQuery_whenParamsNotEmpty() {
        Mockito.when(carsRepository.findByQuery(eq("BMW"), any(), any(), any(), any(), any(), eq(Sort.by(Sort.Direction.ASC, "brand")))).thenReturn(List.of(EXAMPLE_CAR));
        List<CarDTO> responseCars = carsService.getCars(Map.of("brand", "BMW"));
        Assertions.assertEquals(List.of(EXAMPLE_CAR_DTO), responseCars);
        Mockito.verify(carsRepository, times(1)).findByQuery(eq("BMW"), any(), any(), any(), any(), any(), eq(Sort.by(Sort.Direction.ASC, "brand")));
    }
}
