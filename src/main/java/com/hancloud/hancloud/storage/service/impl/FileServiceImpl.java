package com.hancloud.hancloud.storage.service.impl;

import com.hancloud.hancloud.storage.exception.FileNotFoundException;
import com.hancloud.hancloud.storage.exception.FileUploadException;
import com.hancloud.hancloud.storage.service.FileService;
import com.hancloud.hancloud.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private static final String DEFAULT_FILE_PATH = "storage/";

    @Override
    public void storage(MultipartFile[] files) {

    }

    @Override
    public String storage(MultipartFile file, String filePath) {
        String fileException = FileUtil.getFileExtension(file);
        UUID fileName = UUID.randomUUID();

        String nowFileName = fileName + fileException;

        Path path = Paths.get(DEFAULT_FILE_PATH + filePath);

        if(!Files.exists(path)) {
            throw new FileNotFoundException(filePath +" 를 찾을 수 없습니다.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path realPath = path.resolve(nowFileName);
            Files.copy(inputStream, realPath, StandardCopyOption.REPLACE_EXISTING);
            return fileName.toString();
        } catch (IOException e) {
            throw new FileUploadException("파일을 저장할 수 없습니다.");
        }
    }
}
