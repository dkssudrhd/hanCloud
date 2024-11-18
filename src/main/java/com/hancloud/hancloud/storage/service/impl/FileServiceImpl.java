package com.hancloud.hancloud.storage.service.impl;

import com.hancloud.hancloud.storage.exception.*;
import com.hancloud.hancloud.storage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private static final String DEFAULT_FILE_PATH = "storage/";

    @Override
    public void storage(MultipartFile[] files, String filePath) {
        for(int i=1; i<=files.length; i++) {
            try{
                storage(files[i], filePath);
            } catch (Exception e) {
                log.error("{}번째 파일 저장 오류 : {}", i, e.getMessage());
            }
        }
    }

    @Override
    public void storage(MultipartFile file, String filePath) {

        String nowFileName = getFileName(file);
        Path path = Paths.get(DEFAULT_FILE_PATH + filePath);
        filePathIsExists(path);

        Path storagePath = path.resolve(nowFileName);
        fileNameDuplicationCheck(storagePath);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, storagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("{} 파일 저장오류", nowFileName);
            throw new FileUploadException(nowFileName + " 파일을 저장할 수 없습니다.");
        }
    }

    @Override
    public String getFileExtension(MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        if(fileName == null || !fileName.contains(".")){
            log.error("파일 확장자 발견 실패");
            throw new FileNotFoundException("파일의 확장자를 발견하지 못하였습니다.");
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String getFileName(MultipartFile file) {
        String nowFileName = file.getOriginalFilename();
        if(nowFileName == null){
            log.error("파일을 찾을 수 없음");
            throw new FileUploadException("파일을 찾을 수 없습니다.");
        }
        return nowFileName;
    }

    @Override
    public void filePathIsExists(Path path) {
        if(!Files.exists(path)) {
            log.error("{} 를 찾지 못함", path);
            throw new FileNotFoundException(path + " 를 찾을 수 없습니다.");
        }
    }

    @Override
    public void fileNameDuplicationCheck(Path path) {
        if(Files.exists(path)){
            log.error("{} 파일이름 중복", path);
            throw new FileNameDuplicationException(path + " 라는 파일이 이미 있습니다.");
        }
    }

    @Override
    public Resource loadFileAsResource(String filePath) {
        Path uploadPath = Paths.get(DEFAULT_FILE_PATH);
        try {
            Path path = uploadPath.resolve(filePath).normalize();
            Resource resource = new UrlResource(path.toUri());
            filePathIsExists(path);

            return resource;
        } catch (MalformedURLException ex) {
            log.error("로딩 중 {} 파일을 찾을 수 없음", filePath);
            throw new FileNotFoundException(filePath + " 파일을 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteFile(String filePath) {
        File file = new File(DEFAULT_FILE_PATH + filePath);
        if(file.exists()){
            boolean result = file.delete();
            if(!result){
                log.error("삭제 중 {} 파일 삭제 오류", filePath);
                throw new FileDeleteFailedException(filePath + " 파일 삭제에 오류가 발생하였습니다.");
            }
        } else{
            log.error("삭제 중 {} 파일을 찾을 수 없음", filePath);
            throw new FileNotFoundException(filePath + " 파일을 찾을 수 없습니다.");
        }
    }

    @Override
    public void storageAdd(String filePath) {

        Path path = Paths.get(DEFAULT_FILE_PATH + filePath);
        if(Files.exists(path)) {
            log.error("{} 에 폴더가 이미 존재", path);
            throw new FileAlreadyExistsException(path + " 에 폴더가 이미 존재 합니다.");
        }
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("{} 를 만드는 도중 오류", filePath);
            throw new FileCreateException(filePath + "를 만드는 도중 오류가 발생하였습니다.");
        }
    }

    @Override
    public void storageRemove(String filePath) {
        Path path = Paths.get(DEFAULT_FILE_PATH + filePath);

        if(Files.isDirectory(path)) {
            File[] files = path.toFile().listFiles();
            if (files == null) {
                deleteFile(filePath);
                return;
            }
            for(File file : files) {
                if(file.isDirectory()) {
                    log.info(file.getPath().substring(DEFAULT_FILE_PATH.length()));
                    storageRemove(file.getPath().substring(DEFAULT_FILE_PATH.length()));

                }else{
                    boolean result = file.delete();
                    if(!result){
                        log.error("폴더안 파일 삭제 중 {} 파일 삭제 오류", filePath);
                        throw new FileDeleteFailedException(file.getName() + " 폴더안 파일 삭제에 오류가 발생하였습니다.");
                    }
                }
            }
            deleteFile(filePath);
        } else{
            throw new FileNotFoundException(filePath + " 해당 폴더가 없습니다.");
        }
    }
}
