package com.hancloud.hancloud.util;

import com.hancloud.hancloud.storage.exception.FileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static String getFileExtension(MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        if(fileName == null || !fileName.contains(".")){
            throw new FileNotFoundException("파일의 확장자를 발견하지 못하였습니다.");
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
