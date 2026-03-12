package com.main.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile file, String context);

    String uploadImageBytes(byte[] imageData, String contentType, String context);

    String getImageUrl(String objectKey);

    void deleteImage(String objectKeyOrUrl);
}
