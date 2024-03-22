package com.example.turboaz.service;

import com.example.turboaz.dao.ImageEntity;
import com.example.turboaz.dao.ImageRepository;
import com.example.turboaz.exception.ImageNotFoundException;
import com.example.turboaz.mapper.ImageMapper;
import com.example.turboaz.model.ImageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public void uploadImage(ImageDTO newImage) {
        log.info("Creating new image: {}", newImage);
        ImageEntity imageEntity = imageMapper.mapToEntity(newImage);
        imageRepository.save(imageEntity);
        log.info("Image created successfully: {}", newImage);
    }

    public void deleteImage(Integer imageId) {
        log.info("Deleting image with ID: {}", imageId);
        if (imageRepository.existsById(imageId)) {
            imageRepository.deleteById(imageId);
            log.info("Image deleted successfully with ID: {}", imageId);
        } else {
            log.error("Image not found with ID: {}", imageId);
            throw new ImageNotFoundException("Image not found with ID: " + imageId);
        }
    }

    public void updateImage(Integer imageId, ImageDTO updatedImage) {
        log.info("Updating image with ID: {}", imageId);
        if (imageRepository.existsById(imageId)) {
            ImageEntity existingImage = imageRepository.findById(imageId).orElseThrow(
                    () -> new ImageNotFoundException("Image not found with ID: " + imageId));
            ImageEntity updatedImageEntity = imageMapper.mapToEntity(updatedImage);
            updatedImageEntity.setImageId(existingImage.getImageId());
            imageRepository.save(updatedImageEntity);
            log.info("Image updated successfully with ID: {}", imageId);
        } else {
            log.error("Image not found with ID: {}", imageId);
            throw new ImageNotFoundException("Image not found with ID: " + imageId);
        }
    }

    public Page<ImageDTO> listImages(Long carId, Pageable pageable){
        log.info("Fetching images for car ID: {}", carId);
        Page<ImageEntity> imagePage = imageRepository.findByCarEntityCarId(carId, pageable);
        log.info("Images fetched successfully for car ID: {}", carId);
        return imagePage.map(imageMapper::mapToDTO);
    }

}