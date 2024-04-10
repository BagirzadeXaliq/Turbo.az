package com.example.turboaz.service.impl;

import com.example.turboaz.dao.entity.ImageEntity;
import com.example.turboaz.dao.repository.ImageRepository;
import com.example.turboaz.exception.NotFoundException;
import com.example.turboaz.mapper.ImageMapper;
import com.example.turboaz.model.ImageDTO;
import com.example.turboaz.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public ImageDTO upload(ImageDTO newImage) {
        log.info("Creating new image: {}", newImage);
        ImageEntity imageEntity = imageMapper.mapToEntity(newImage);
        imageEntity = imageRepository.save(imageEntity);
        ImageDTO uploadedImage = imageMapper.mapToDTO(imageEntity);
        log.info("Image created successfully: {}", uploadedImage);
        return uploadedImage;
    }

    @Override
    public void delete(Integer imageId) {
        log.info("Deleting image with ID: {}", imageId);
        imageRepository.deleteById(imageId);
        log.info("Image deleted successfully with ID: {}", imageId);
    }

    @Override
    public void update(Integer imageId, ImageDTO updatedImage) {
        log.info("Updating image with ID: {}", imageId);
        ImageEntity existingImage = imageRepository.findById(imageId).orElseThrow(
                () -> new NotFoundException("Image not found with ID: " + imageId));
        ImageEntity updatedImageEntity = imageMapper.mapToEntity(updatedImage);
        updatedImageEntity.setImageId(existingImage.getImageId());
        imageRepository.save(updatedImageEntity);
        log.info("Image updated successfully with ID: {}", imageId);
    }

    @Override
    public Page<ImageDTO> getList(Long carId, Pageable pageable){
        log.info("Fetching images for car ID: {}", carId);
        Page<ImageEntity> imagePage = imageRepository.findByCarEntityCarId(carId, pageable);
        log.info("Images fetched successfully for car ID: {}", carId);
        return imagePage.map(imageMapper::mapToDTO);
    }

}