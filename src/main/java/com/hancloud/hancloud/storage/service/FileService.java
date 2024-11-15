package com.hancloud.hancloud.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void storage(MultipartFile[] files);
    String storage(MultipartFile file, String filePath);
}
