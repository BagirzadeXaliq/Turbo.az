package com.example.turboaz.service;

import com.example.turboaz.model.ImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageService {

    ImageDTO upload(ImageDTO newImage);

    void delete(Integer imageId);

    void update(Integer imageId, ImageDTO updatedImage);

    Page<ImageDTO> getList(Long carId, Pageable pageable);

}
