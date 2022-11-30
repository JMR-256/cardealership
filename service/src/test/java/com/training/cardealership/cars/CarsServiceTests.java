package com.training.cardealership.cars;

import com.training.cardealership.exceptions.CarExistsException;
import com.training.cardealership.exceptions.EntityNotFoundException;
import com.training.cardealership.exceptions.InvalidQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTests {

    @InjectMocks
    private CarsService carsService;

    @Mock
    private CarsRepository carsRepository;

    private Car exampleCar;
    private CarDTO exampleCarDto;

    @BeforeEach
    private void init() {
        exampleCar = new Car("BMW", "X5", 80000, 2022, 10000, "Space Grey");
        exampleCarDto = new CarDTO("BMW", "X5", 80000, 2022, 10000, "Space Grey");
    }


    @Test
    void add_car_callsRepository_correctNumber_of_time() {
        List<CarDTO> cars = List.of(exampleCarDto);
        carsService.addCar(cars);
        Mockito.verify(carsRepository, times(1)).save(exampleCar);
    }

    @Test
    void throws_exception_when_data_not_valid() {
        Mockito.when(carsRepository.save(exampleCar)).thenThrow(ConstraintViolationException.class);
        List<CarDTO> cars = List.of(exampleCarDto);
        Assertions.assertThrows(ConstraintViolationException.class, () -> carsService.addCar(cars));
        Mockito.verify(carsRepository, times(1)).save(exampleCar);
    }

    @Test
    void throws_correct_exception_when_duplicate_car_exists() {
        Mockito.when(carsRepository.existsByBrandIgnoreCaseAndModelIgnoreCase(exampleCarDto.getBrand(), exampleCarDto.getModel())).thenReturn(false).thenReturn(true);

        Assertions.assertThrows(CarExistsException.class, () -> carsService.addCar(List.of(exampleCarDto, exampleCarDto)));
    }

    @Test
    void findCars_calls_repo_findAll_whenParamsEmpty() {
        Mockito.when(carsRepository.findAll(any(Sort.class))).thenReturn(List.of(exampleCar));
        List<CarDTO> responseCars = carsService.getCars(new HashMap<>());
        Assertions.assertEquals(List.of(exampleCarDto), responseCars);
        Mockito.verify(carsRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void findCars_throwsException_whenUsingUnexpectedQueryParam() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("Invalid", "param")));
    }

    @Test
    void findCars_throwsException_whenYearQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("year", "fsdj")));
    }

    @Test
    void findCars_throwsException_whenMileageQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("mileage", "fsdj")));
    }

    @Test
    void findCars_throwsException_whenPriceQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("price", "fsdj")));
    }

    @Test
    void findCars_throwsException_whenUsingPriceQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("price", "fsdj")));
    }

    @Test
    void findCars_throwsException_whenUsingBrandQueryParamContainsWhitespace() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("brand", "fsdj fdsha")));
    }

    @Test
    void findCars_throwsException_whenUsingBrandQueryParamContainsSpecialCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("brand", "fsdj!fdsha")));
    }

    @Test
    void findCars_throwsException_whenUsingModelQueryParamContainsSpecialCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, () -> carsService.getCars(Map.of("model", "fsdj!fdsha")));
    }

    @Test
    void updateCars_throwsException_if_carDoesNotExist() {
        Mockito.when(carsRepository.findByBrandIgnoreCaseAndModelIgnoreCase(any(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> carsService.updateCar(exampleCarDto));
        Mockito.verify(carsRepository, times(1)).findByBrandIgnoreCaseAndModelIgnoreCase(any(), any());
    }

    @Test
    void updateCars_callsFindCar_and_Save() {
        Car updatedCar = new Car(exampleCar.getBrand(), exampleCar.getModel(), exampleCar.getPrice(), exampleCar.getYear(), exampleCar.getMileage(), "Pink");
        CarDTO updatedCarDTO = new CarDTO(exampleCar.getBrand(), exampleCar.getModel(), exampleCar.getPrice(), exampleCar.getYear(), exampleCar.getMileage(), "Pink");

        Mockito.when(carsRepository.findByBrandIgnoreCaseAndModelIgnoreCase(exampleCar.getBrand(), exampleCar.getModel())).thenReturn(Optional.of(exampleCar));
        carsService.updateCar(updatedCarDTO);
        Mockito.verify(carsRepository, times(1)).findByBrandIgnoreCaseAndModelIgnoreCase(any(), any());
        Mockito.verify(carsRepository, times(1)).save(updatedCar);
    }

    @Test
    void findCars_calls_repo_findByQuery_whenParamsNotEmpty() {
        Mockito.when(carsRepository.findByQuery(eq("BMW"), any(), any(), any(), any(), any(), eq(Sort.by(Sort.Direction.ASC, "brand")))).thenReturn(List.of(exampleCar));
        List<CarDTO> responseCars = carsService.getCars(Map.of("brand", "BMW"));
        Assertions.assertEquals(List.of(exampleCarDto), responseCars);
        Mockito.verify(carsRepository, times(1)).findByQuery(eq("BMW"), any(), any(), any(), any(), any(), eq(Sort.by(Sort.Direction.ASC, "brand")));
    }
}
