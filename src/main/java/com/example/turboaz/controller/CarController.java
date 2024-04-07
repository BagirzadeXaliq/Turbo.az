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
    public void add(@Valid @RequestBody CarDTO newCar) {
        carService.add(newCar);
    }

    @DeleteMapping("/{carId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer carId) {
        carService.delete(carId);
    }

    @PutMapping("/{carId}")
    public void updateDetails(@PathVariable Integer carId, @Valid @RequestBody CarDTO updatedCar) {
        carService.updateDetails(carId, updatedCar);
    }

    @GetMapping ("/{carId}")
    public CarDTO viewDetails(@PathVariable Integer carId) {
        return carService.viewDetails(carId);
    }

    @GetMapping
    public Page<CarDTO> getList(@Valid CarFilterDTO carFilterDto, Pageable pageable) {
        return carService.getList(carFilterDto, pageable);
    }
}