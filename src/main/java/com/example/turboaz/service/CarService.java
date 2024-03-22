package com.example.turboaz.service;

import com.example.turboaz.dao.CarEntity;
import com.example.turboaz.dao.CarRepository;
import com.example.turboaz.exception.CarNotFoundException;
import com.example.turboaz.mapper.CarMapper;
import com.example.turboaz.model.CarDTO;
import com.example.turboaz.model.CarFilterDTO;
import com.example.turboaz.service.specification.CarSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public void deleteCarsOlderThan(LocalDate date) {
        log.info("Deleting cars older than: {}", date);
        carRepository.deleteByYearBefore(date.getYear());
        log.info("Cars older than {} deleted successfully", date);
    }

    public void addCar(CarDTO newCar) {
        log.info("Creating new car: {}", newCar);
        CarEntity carEntity = carMapper.mapToEntity(newCar);
        carRepository.save(carEntity);
        log.info("New car created successfully: {}", newCar);
    }

    public void deleteCar(Integer carId) {
        log.info("Deleting car with ID: {}", carId);
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
            log.info("Car with ID {} deleted successfully", carId);
        } else {
            log.error("Car not found with ID: {}", carId);
            throw new CarNotFoundException("Car not found with ID: " + carId);
        }
    }

    public void updateCarDetails(Integer carId, CarDTO updatedCar) {
        log.info("Updating car details for car ID: {}", carId);
        if (carRepository.existsById(carId)) {
            CarEntity existingCar = carRepository.findById(carId).orElseThrow(
                    () -> new CarNotFoundException("Car not found with ID: " + carId));
            CarEntity updatedCarEntity = carMapper.mapToEntity(updatedCar);
            updatedCarEntity.setCarId(existingCar.getCarId());
            carRepository.save(updatedCarEntity);
            log.info("Car details updated successfully for car ID: {}", carId);
        } else {
            log.error("Car not found with ID: {}", carId);
            throw new CarNotFoundException("Car not found with ID: " + carId);
        }
    }

    public CarDTO viewCarDetails(Integer carId){
        log.info("Fetching car details for car ID: {}", carId);
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found with ID: " + carId));
        log.info("Car details fetched successfully for car ID: {}", carId);
        return carMapper.mapToDTO(carEntity);
    }


    public Page<CarDTO> listCars(CarFilterDTO carFilterDto, Pageable pageable) {
        log.info("Fetching list of cars with filter: {}", carFilterDto);
        var specifications = new CarSpecification().getCarSpecification(carFilterDto);
        Page<CarEntity> carPage = carRepository.findAll(specifications, pageable);
        log.info("List of cars fetched successfully with filter: {}", carFilterDto);
        return carPage.map(carMapper::mapToDTO);
    }

}