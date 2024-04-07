package com.example.turboaz.service;

import com.example.turboaz.model.CarDTO;
import com.example.turboaz.model.CarFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

public interface CarService {

    void deleteCarsOlderThan(LocalDate date);

    void add(CarDTO newCar);

    void delete(Integer carId);

    void updateDetails(Integer carId, CarDTO updatedCar);

    CarDTO viewDetails(Integer carId);

    Page<CarDTO> getList(CarFilterDTO carFilterDto, Pageable pageable);

}