package com.hancloud.hancloud.storage.controller;

import com.hancloud.hancloud.storage.service.FileService;
import com.hancloud.hancloud.util.ApiMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    public ApiMessage uploadFile(@RequestParam("files") MultipartFile[] files,
                                 @RequestParam("path") String path) {
        Response res = new Response();
        List<String> results = new ArrayList<>();
        List<String> imageLocations = new ArrayList<>();
//        try{
//            results = storageService.saveFiles(files, postName);
//            for(String result : results){
//                imageLocations.add("/"+postName+"/"+result);
//            }
//            res.setImageLocations(imageLocations);
//            res.setMessage("done");
//            res.setSuccess(true);
//            return new ResponseEntity<Response>(res, HttpStatus.OK);
//        }catch (Exception e){
//            res.setMessage("failed");
//            res.setSuccess(false);
//            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return ApiMessage.success("성공");
    }
}
