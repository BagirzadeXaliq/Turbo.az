package com.example.turboaz.controller;

import com.example.turboaz.model.CarDTO;
import com.example.turboaz.model.CarFilterDTO;
import com.example.turboaz.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCar(@Valid @RequestBody CarDTO newCar) {
        carService.addCar(newCar);
    }

    @DeleteMapping("/{carId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Integer carId) {
        carService.deleteCar(carId);
    }

    @PutMapping("/{carId}")
    public void updateCarDetails(@PathVariable Integer carId, @Valid @RequestBody CarDTO updatedCar) {
        carService.updateCarDetails(carId, updatedCar);
    }

    @GetMapping ("/details/{carId}")
    public CarDTO viewCarDetails(@PathVariable Integer carId) {
        return carService.viewCarDetails(carId);
    }

    @GetMapping("/list")
    public Page<CarDTO> listCars(@Valid CarFilterDTO carFilterDto, Pageable pageable) {
        return carService.listCars(carFilterDto, pageable);
    }
}