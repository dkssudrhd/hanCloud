package com.hancloud.hancloud.storage.controller;

import com.hancloud.hancloud.storage.dto.response.FileSuccessResponse;
import com.hancloud.hancloud.storage.service.FileService;
import com.hancloud.hancloud.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일에 관한 내용이 있는 controller
 *
 * @author 한민기
 */
@Slf4j
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;

    /**
     * 여러개의 파일 업로드
     *
     * @param files         저장할 파일
     * @param path          저장할 파일의 위치
     * @return              성공여부 응답
     */
    @PostMapping("/multi")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FileSuccessResponse> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
                                 @RequestParam("path") String path) {
        fileService.storage(files, path);
        return ApiResponse.createdSuccess(FileSuccessResponse.builder().message(files.length + "개 파일 저장 성공").build());
    }

    /**
     * 파일 한개 업로드
     *
     * @param file          저장할 파일
     * @param path          저장할 파일의 위치
     * @return              성공여부 응답
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FileSuccessResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path){
        fileService.storage(file, path);

        return ApiResponse.createdSuccess(FileSuccessResponse.builder().message("파일 저장 성공").build());
    }

    /**
     * 폴더 생성
     *
     * @param path          파일의 위치
     * @return              성공여부 응답
     */
    @PostMapping("/folder")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FileSuccessResponse> uploadFolder(
            @RequestParam("path") String path){
        fileService.storageAdd(path);

        return ApiResponse.createdSuccess(FileSuccessResponse.builder().message("폴더 생성 성공").build());
    }

    /**
     * 파일 하나만 삭제할 경우
     *
     * @param path          삭제할 파일의 위치
     * @return              성공 여부
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<FileSuccessResponse> deleteFile(
            @RequestParam("path") String path){
        fileService.deleteFile(path);
        return ApiResponse.deleteSuccess(FileSuccessResponse.builder().message("파일 삭제 성공").build());
    }

    /**
     * 폴더 삭제할 경우
     *
     * @param path          삭제할 폴더의 위치
     * @return              성공 여부
     */
    @DeleteMapping("/folder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<FileSuccessResponse> deleteFolder(
            @RequestParam("path") String path){
        fileService.storageRemove(path);
        return ApiResponse.deleteSuccess(FileSuccessResponse.builder().message("폴더 삭제 성공").build());
    }

    /**
     * 이미지의 파일일 경우 웹페이지를 통해 확인
     *
     * @param path          읽을 파일 위치
     * @return              파일 리턴
     */
    @GetMapping("/load")
    public ResponseEntity<Resource> loadImageFile(
            @RequestParam("path") String path){

        log.info(SecurityUtils.getCurrentAuthorities().toString());
        fileService.isImage(path);
        Resource resource = fileService.loadFileAsResource(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+ resource.getFilename()+ "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    /**
     * 파일 다운로드
     *
     * @param path          읽을 파일 위치
     * @return              파일 리턴
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("path") String path){
        Resource resource = fileService.loadFileAsResource(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+ resource.getFilename()+ "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
