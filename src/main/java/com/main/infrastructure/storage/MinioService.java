package com.main.infrastructure.storage;

import java.io.InputStream;

public interface MinioService {

    void putObject(String bucket, String objectName, InputStream content, long size, String contentType);

    String getObjectUrl(String bucket, String objectName);

    void removeObject(String bucket, String objectName);
}
