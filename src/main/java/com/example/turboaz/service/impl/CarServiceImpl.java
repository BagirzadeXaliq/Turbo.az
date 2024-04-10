package com.example.turboaz.service.impl;

import com.example.turboaz.dao.entity.CarEntity;
import com.example.turboaz.dao.repository.CarRepository;
import com.example.turboaz.exception.NotFoundException;
import com.example.turboaz.mapper.CarMapper;
import com.example.turboaz.model.CarDTO;
import com.example.turboaz.model.CarFilterDTO;
import com.example.turboaz.service.CarService;
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
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public void deleteCarsOlderThan(LocalDate date) {
        log.info("Deleting cars older than: {}", date);
        Long year = (long) date.getYear();
        carRepository.deleteByYearBefore(year);
        log.info("Cars older than {} deleted successfully", date);
    }

    @Override
    public CarDTO add(CarDTO newCar) {
        log.info("Adding new car: {}", newCar);
        CarEntity carEntity = carMapper.mapToEntity(newCar);
        carEntity = carRepository.save(carEntity);
        CarDTO addedCar = carMapper.mapToDTO(carEntity);
        log.info("New car added successfully: {}", addedCar);
        return addedCar;
    }

    @Override
    public void delete(Integer carId) {
        log.info("Deleting car with ID: {}", carId);
        carRepository.deleteById(carId);
        log.info("Car with ID {} deleted successfully", carId);
    }

    @Override
    public void updateDetails(Integer carId, CarDTO updatedCar) {
        log.info("Updating car details for car ID: {}", carId);
        CarEntity existingCar = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with ID: " + carId));
        CarEntity updatedCarEntity = carMapper.mapToEntity(updatedCar);
        updatedCarEntity.setCarId(existingCar.getCarId());
        carRepository.save(updatedCarEntity);
        log.info("Car details updated successfully for car ID: {}", carId);
    }

    @Override
    public CarDTO viewDetails(Integer carId){
        log.info("Fetching car details for car ID: {}", carId);
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with ID: " + carId));
        log.info("Car details fetched successfully for car ID: {}", carId);
        return carMapper.mapToDTO(carEntity);
    }

    @Override
    public Page<CarDTO> getList(Pageable pageable) {
        log.info("Fetching list of cars");
//        var specifications = new CarSpecification().getCarSpecification(carFilterDto);
        Page<CarEntity> carPage = carRepository.findAll(pageable);
        log.info("List of cars fetched successfully with filter");
        return carPage.map(carMapper::mapToDTO);
    }

}