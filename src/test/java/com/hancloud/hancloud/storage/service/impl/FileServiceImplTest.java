//package com.hancloud.hancloud.storage.service.impl;
//
//import com.hancloud.hancloud.storage.exception.FileAlreadyExistsException;
//import com.hancloud.hancloud.storage.exception.FileNameDuplicationException;
//import com.hancloud.hancloud.storage.service.FileService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.core.io.Resource;
//import org.springframework.mock.web.MockMultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class FileServiceImplTest {
//    private FileService fileService;
//
//    @BeforeEach
//    void setUp() {
//        fileService = new FileServiceImpl();
//    }
//    @Test
//    @DisplayName("파일 저장")
//    void realStorage_success() throws IOException {
//        File file = new File("storageTest/kafka.png");
//        FileInputStream inputStream = new FileInputStream(file);
//        MockMultipartFile multipartFile = new MockMultipartFile(
//                "file",                      // 필드 이름
//               file.getName(),                    // 파일 이름
//               "application/octet-stream",        // MIME 타입 (필요에 따라 설정)
//               inputStream                        // 파일 데이터
//       );
//        fileService.storage(multipartFile, "test");
//
//    }
//
//    @Test
//    @DisplayName("파일 경로가 잘못될 경우")
//    void testStorage_invalidPath() {
//        // Given
//        String invalidFilePath = "invalid_path/?<>*";
//        MockMultipartFile mockFile = new MockMultipartFile(
//                "file",
//                "testFile.txt",
//                "text/plain",
//                "content".getBytes()
//        );
//
//        assertThrows(RuntimeException.class, () -> fileService.storage(mockFile, invalidFilePath));
//    }
//
//    @Test
//    @DisplayName("파일 이름 중복 확인")
//    void testFileNameDuplicationCheck(){
//        String fileName = "storage/test/kafka.png";
//        Path path = Paths.get(fileName);
//        assertThrows(FileNameDuplicationException.class, ()-> fileService.fileNameDuplicationCheck(path));
//    }
//
//    @Test
//    @DisplayName("파일 로딩 확인")
//    void testLoadFileAsResource(){
//        String fileName = "test/kafka.png";
//        Resource resource = fileService.loadFileAsResource(fileName);
//
//        assertNotNull(resource);
//    }
//
//    @Test
//    @DisplayName("파일 삭제 확인")
//    void testDeleteFile(){
//        String fileName = "test/kafka.png";
//        fileService.deleteFile(fileName);
//    }
//
//    @Test
//    @DisplayName("폴더 생성 확인")
//    void testCreateFileFolder(){
//        String fileName = "test/a";
//        fileService.storageAdd(fileName);
//    }
//    @Test
//    @DisplayName("폴더 생성 확인")
//    void testCreateFileFolder2(){
//        String fileName = "test/a/b/c";
//        fileService.storageAdd(fileName);
//    }
//    @Test
//    @DisplayName("폴더 생성 중복 확인")
//    void testCreateFileDuplication(){
//        String fileName = "test/duplication";
//        assertThrows(FileAlreadyExistsException.class ,  ()-> fileService.storageAdd(fileName));
//    }
//
//    @Test
//    @DisplayName("폴더 삭제 확인")
//    void testDeleteFile2(){
//        String fileName = "test/a/b";
//        fileService.storageRemove(fileName);
//    }
//
//}
