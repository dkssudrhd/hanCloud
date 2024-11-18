package com.hancloud.hancloud.storage.controller;

import com.hancloud.hancloud.storage.dto.response.FileSuccessResponse;
import com.hancloud.hancloud.storage.service.FileService;
import com.hancloud.hancloud.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * 여러개의 파일 업로드
     *
     * @param apiId         API 아이디
     * @param apiPassword   API 비밀번호
     * @param files         저장할 파일
     * @param path          저장할 파일의 위치
     * @return              성공여부 응답
     */
    @PostMapping("/multi")
    public ApiResponse<FileSuccessResponse> uploadFiles(
            @RequestHeader(value = "API-ID") String apiId,
            @RequestHeader(value = "API-PASSWORD") String apiPassword,
            @RequestParam("files") MultipartFile[] files,
                                 @RequestParam("path") String path) {
        fileService.storage(files, path);
        return ApiResponse.success(FileSuccessResponse.builder().message(files.length + "개 파일 저장 성공").build());
    }

    /**
     * 파일 한개 업로드
     *
     * @param apiId         API 아이디
     * @param apiPassword   API 비밀번호
     * @param file          저장할 파일
     * @param path          저장할 파일의 위치
     * @return              성공여부 응답
     */
    @PostMapping
    public ApiResponse<FileSuccessResponse> uploadFile(
            @RequestHeader(value = "API-ID") String apiId,
            @RequestHeader(value = "API-PASSWORD") String apiPassword,
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path
    ){
        fileService.storage(file, path);
        return ApiResponse.success(FileSuccessResponse.builder().message("파일 저장 성공").build());
    }
}
