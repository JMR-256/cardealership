package com.training.cardealership.cars;

import com.training.cardealership.exceptions.CarExistsException;
import com.training.cardealership.exceptions.EntityNotFoundException;
import com.training.cardealership.exceptions.InvalidQueryException;
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
    void findCars_throwsException_whenUsingUnexpectedQueryParam() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("Invalid", "param")));
    }

    @Test
    void findCars_throwsException_whenYearQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("year", "fsdj")));
    }
    @Test
    void findCars_throwsException_whenMileageQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("mileage", "fsdj")));
    }

    @Test
    void findCars_throwsException_whenPriceQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("price", "fsdj")));
    }

    @Test
    void findCars_throwsException_whenUsingPriceQueryParamContainsCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("price", "fsdj")));
    }

    @Test
    void findCars_throwsException_whenUsingBrandQueryParamContainsWhitespace() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("brand", "fsdj fdsha")));
    }

    @Test
    void findCars_throwsException_whenUsingBrandQueryParamContainsSpecialCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("brand", "fsdj!fdsha")));
    }

    @Test
    void findCars_throwsException_whenUsingModelQueryParamContainsSpecialCharacters() {
        Assertions.assertThrows(InvalidQueryException.class, ()-> carsService.getCars(Map.of("model", "fsdj!fdsha")));
    }

    @Test
    void updateCars_throwsException_if_carDoesNotExist() {
        Mockito.when(carsRepository.findByBrandIgnoreCaseAndModelIgnoreCase(any(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> carsService.updateCar(EXAMPLE_CAR_DTO));
        Mockito.verify(carsRepository, times(1)).findByBrandIgnoreCaseAndModelIgnoreCase(any(), any());
    }

    @Test
    void updateCars_callsFindCar_and_Save() {
        Car updatedCar = new Car(EXAMPLE_CAR.getBrand(), EXAMPLE_CAR.getModel(), EXAMPLE_CAR.getPrice(), EXAMPLE_CAR.getYear(), EXAMPLE_CAR.getMileage(), "Pink");
        CarDTO updatedCarDTO = new CarDTO(EXAMPLE_CAR.getBrand(), EXAMPLE_CAR.getModel(), EXAMPLE_CAR.getPrice(), EXAMPLE_CAR.getYear(), EXAMPLE_CAR.getMileage(), "Pink");

        Mockito.when(carsRepository.findByBrandIgnoreCaseAndModelIgnoreCase(EXAMPLE_CAR.getBrand(), EXAMPLE_CAR.getModel())).thenReturn(Optional.of(EXAMPLE_CAR));
        carsService.updateCar(updatedCarDTO);
        Mockito.verify(carsRepository, times(1)).findByBrandIgnoreCaseAndModelIgnoreCase(any(), any());
        Mockito.verify(carsRepository, times(1)).save(updatedCar);
    }

    @Test
    void findCars_calls_repo_findByQuery_whenParamsNotEmpty() {
        Mockito.when(carsRepository.findByQuery(eq("BMW"), any(), any(), any(), any(), any(), eq(Sort.by(Sort.Direction.ASC, "brand")))).thenReturn(List.of(EXAMPLE_CAR));
        List<CarDTO> responseCars = carsService.getCars(Map.of("brand", "BMW"));
        Assertions.assertEquals(List.of(EXAMPLE_CAR_DTO), responseCars);
        Mockito.verify(carsRepository, times(1)).findByQuery(eq("BMW"), any(), any(), any(), any(), any(), eq(Sort.by(Sort.Direction.ASC, "brand")));
    }
}
