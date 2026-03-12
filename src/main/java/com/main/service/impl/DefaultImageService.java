package com.main.service.impl;

import com.main.infrastructure.storage.MinioProperties;
import com.main.infrastructure.storage.MinioService;
import com.main.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
public class DefaultImageService implements ImageService {

    private final MinioService minioService;
    private final MinioProperties minioProperties;

    public DefaultImageService(MinioService minioService, MinioProperties minioProperties) {
        this.minioService = minioService;
        this.minioProperties = minioProperties;
    }

    @Override
    public String uploadImage(MultipartFile file, String context) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode ser nulo ou vazio");
        }
        String extension = getExtension(file.getOriginalFilename());
        String objectName = (context != null && !context.isBlank() ? context + "/" : "") + UUID.randomUUID() + extension;
        String contentType = file.getContentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = "image/jpeg";
        }
        try {
            minioService.putObject(minioProperties.getBucket(), objectName, file.getInputStream(), file.getSize(), contentType);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage(), e);
        }
        return objectName;
    }

    @Override
    public String uploadImageBytes(byte[] imageData, String contentType, String context) {
        if (imageData == null || imageData.length == 0) {
            throw new IllegalArgumentException("Dados da imagem não podem ser nulos ou vazios");
        }
        String objectName = (context != null && !context.isBlank() ? context + "/" : "") + UUID.randomUUID() + ".jpg";
        String type = contentType != null && !contentType.isBlank() ? contentType : "image/jpeg";
        minioService.putObject(minioProperties.getBucket(), objectName, new ByteArrayInputStream(imageData), imageData.length, type);
        return objectName;
    }

    @Override
    public String getImageUrl(String objectKey) {
        if (objectKey == null || objectKey.isBlank()) {
            return null;
        }
        return minioService.getObjectUrl(minioProperties.getBucket(), objectKey);
    }

    @Override
    public void deleteImage(String objectKeyOrUrl) {
        if (objectKeyOrUrl == null || objectKeyOrUrl.isBlank()) {
            return;
        }
        String objectKey = extractObjectKey(objectKeyOrUrl);
        if (objectKey != null) {
            minioService.removeObject(minioProperties.getBucket(), objectKey);
        }
    }

    private static String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return "." + filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    private String extractObjectKey(String objectKeyOrUrl) {
        if (objectKeyOrUrl.contains("/")) {
            int idx = objectKeyOrUrl.indexOf(minioProperties.getBucket() + "/");
            if (idx >= 0) {
                return objectKeyOrUrl.substring(idx + minioProperties.getBucket().length() + 1);
            }
            if (objectKeyOrUrl.startsWith("http")) {
                int lastSlash = objectKeyOrUrl.lastIndexOf('/');
                if (lastSlash >= 0 && lastSlash < objectKeyOrUrl.length() - 1) {
                    String possibleKey = objectKeyOrUrl.substring(lastSlash + 1);
                    if (possibleKey.contains("?")) {
                        return possibleKey.substring(0, possibleKey.indexOf('?'));
                    }
                    return possibleKey;
                }
            }
        }
        return objectKeyOrUrl;
    }
}
