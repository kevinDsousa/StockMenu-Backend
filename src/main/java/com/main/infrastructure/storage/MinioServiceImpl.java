package com.main.infrastructure.storage;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final MinioProperties properties;

    public MinioServiceImpl(MinioClient minioClient, MinioProperties properties) {
        this.minioClient = minioClient;
        this.properties = properties;
    }

    @Override
    public void putObject(String bucket, String objectName, InputStream content, long size, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(content, size, -1)
                            .contentType(contentType != null ? contentType : "application/octet-stream")
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar objeto ao MinIO: " + e.getMessage(), e);
        }
    }

    @Override
    public String getObjectUrl(String bucket, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    io.minio.GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter URL do objeto: " + e.getMessage(), e);
        }
    }

    @Override
    public void removeObject(String bucket, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover objeto do MinIO: " + e.getMessage(), e);
        }
    }
}
