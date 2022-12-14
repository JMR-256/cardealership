package com.training.cardealership.cars;

import com.training.cardealership.enums.ExceptionsEnum;
import com.training.cardealership.exceptions.CarExistsException;
import com.training.cardealership.exceptions.EntityNotFoundException;
import com.training.cardealership.exceptions.InvalidQueryException;
import com.training.cardealership.validation.QueryValidator;
import com.training.cardealership.validation.StringValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsService {

    @Autowired
    private CarsRepository carsRepository;

    private static final Sort DEFAULT_CAR_SORT = Sort.by(Sort.Direction.ASC, "brand");
    private static final QueryValidator queryValidator = new QueryValidator(true)
            .addValidationRule("brand", List.of(StringValidators.notEmpty, StringValidators.notContainingSpecials, StringValidators.notContainingWhitespace))
            .addValidationRule("model", List.of(StringValidators.notEmpty, StringValidators.notContainingSpecials))
            .addValidationRule("price", List.of(StringValidators.notEmpty, StringValidators.isInteger))
            .addValidationRule("year", List.of(StringValidators.notEmpty, StringValidators.isInteger, new StringValidators.LengthValidator(4)))
            .addValidationRule("mileage", List.of(StringValidators.notEmpty, StringValidators.isInteger))
            .addValidationRule("colour", List.of(StringValidators.notEmpty, StringValidators.notContainingSpecials));

    public CarsService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    public void addCar(List<CarDTO> cars) {
        cars.stream().map(this::mapToEntity).forEach(car -> {
            if (carsRepository.existsByBrandIgnoreCaseAndModelIgnoreCase(car.getBrand(), car.getModel())) {
                throw new CarExistsException();
            }
            carsRepository.save(car);
        });
    }

    public List<CarDTO> getCars(Map<String, String> params) {
        if (params.size() == 0)
            return getAllCars();
        else
            return getCarsByQuery(params);
    }

    private List<CarDTO> getAllCars() {
        return mapResponse(carsRepository.findAll(DEFAULT_CAR_SORT));
    }

    private List<CarDTO> getCarsByQuery(Map<String, String> params) {
        queryValidator.validate(params);
        return mapResponse(carsRepository.findByQuery(params.get("brand"), params.get("model"), params.get("price"), params.get("year"), params.get("mileage"), params.get("colour"), DEFAULT_CAR_SORT));
    }

    public void updateCar(CarDTO carDTO) {
        Car car = carsRepository.findByBrandIgnoreCaseAndModelIgnoreCase(carDTO.getBrand(), carDTO.getModel()).orElseThrow(() -> {throw new EntityNotFoundException(ExceptionsEnum.INVALID_CAR);});

        car.setColour(carDTO.getColour());
        car.setMileage(carDTO.getMileage());
        car.setPrice(carDTO.getPrice());
        car.setYear(carDTO.getYear());

        carsRepository.save(car);
    }

    private Car mapToEntity(CarDTO car) {
        return new Car(car.getBrand(), car.getModel(), car.getPrice(), car.getYear(), car.getMileage(), car.getColour());
    }

    private CarDTO mapToDTO(Car car) {
        return new CarDTO(car.getBrand(), car.getModel(), car.getPrice(), car.getYear(), car.getMileage(), car.getColour());
    }

    private List<CarDTO> mapResponse(List<Car> cars) {
        return cars.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void deleteCar(String brand, String model) {
        if (!StringValidators.notEmpty.test(brand) || !StringValidators.notEmpty.test(model)) {
            throw new InvalidQueryException(ExceptionsEnum.DELETE_ERROR);
        }

        Car foundCar = carsRepository.findByBrandIgnoreCaseAndModelIgnoreCase(brand, model).orElseThrow(() -> {
            throw new EntityNotFoundException(ExceptionsEnum.DELETE_ERROR);
        });

        carsRepository.delete(foundCar);

    }
}
