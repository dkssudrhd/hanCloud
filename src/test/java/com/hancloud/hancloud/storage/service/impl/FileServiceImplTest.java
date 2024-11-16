package com.hancloud.hancloud.storage.service.impl;

import com.hancloud.hancloud.storage.exception.FileNameDuplicationException;
import com.hancloud.hancloud.storage.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceImplTest {
    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileServiceImpl(); // 실제 구현체 사용 또는 Mock으로 변경 가능
    }


    @Test
    void realStorage_success() throws IOException {
        File file = new File("storageTest/kafka.png");
        FileInputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",                      // 필드 이름
               file.getName(),                    // 파일 이름
               "application/octet-stream",        // MIME 타입 (필요에 따라 설정)
               inputStream                        // 파일 데이터
       );
        fileService.storage(multipartFile, "dkssudrhd");

    }

    @Test
    void testStorage_invalidPath() {
        // Given
        String invalidFilePath = "invalid_path/?<>*"; // 유효하지 않은 파일 경로
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.txt",
                "text/plain",
                "content".getBytes()
        );

        // When & Then
        assertThrows(RuntimeException.class, () -> fileService.storage(mockFile, invalidFilePath));
    }

    @Test
    void testFileNameDuplicationCheck(){
        String fileName = "storage/dkssudrhd/kafka.png";
        Path path = Paths.get(fileName);
        assertThrows(FileNameDuplicationException.class, ()-> fileService.fileNameDuplicationCheck(path));
    }
}
